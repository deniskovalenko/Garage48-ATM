package com.atm.atm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atm.atm.dummy.DummyContent.ATMEventViewModel;

import java.io.InputStream;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ATMEventViewModel} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ATMEventViewModel item);
    }

    private final List<ATMEventViewModel> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<ATMEventViewModel> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.atm_event_view, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.descriptionText.setText(mValues.get(position).description);
        holder.titleText.setText(mValues.get(position).title);
        holder.distance.setText(mValues.get(position).distance);
        holder.numberOfAttendants.setText(Integer.toString(mValues.get(position).numberOfAttendants));
        holder.timeLeft.setText(mValues.get(position).timeLeft);
        holder.name.setText(mValues.get(position).hostName);
        loadImage(holder.eventPhoto, mValues.get(position).eventPhotoUrl);
        loadImage(holder.hostPhoto, mValues.get(position).hosterPhotoUrl);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    private void loadImage(ImageView imageView, String imageUrl) {
        new DownloadImageTask(imageView).execute(imageUrl);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView titleText;
        public final TextView descriptionText;
        public final TextView distance;
        public final TextView numberOfAttendants;
        public final TextView timeLeft;
        public final ImageView hostPhoto;
        public final ImageView eventPhoto;
        public final TextView name;

        public ATMEventViewModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titleText = (TextView) view.findViewById(R.id.event_header_text);
            descriptionText = (TextView) view.findViewById(R.id.event_description_text);
            distance = (TextView) view.findViewById(R.id.event_distance_text);
            numberOfAttendants = (TextView) view.findViewById(R.id.event_number_attendants);
            timeLeft = (TextView) view.findViewById(R.id.event_time_left);
            name = (TextView) view.findViewById(R.id.host_name);
            hostPhoto = (ImageView) view.findViewById(R.id.host_photo);
            eventPhoto = (ImageView) view.findViewById(R.id.event_photo);
        }

        @Override
        public String toString() {
            return super.toString() + " '" ;//+ mContentView.getText() + "'";
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
