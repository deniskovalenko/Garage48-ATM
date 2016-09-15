package com.atm.atm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.atm.atm.dummy.DummyContent.ATMEventViewModel;
import com.atm.atm.model.EventFromBackend;
import com.atm.atm.model.JoinEventBody;
import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ATMEventViewModel} and makes a call to the
 * specified {@link OnJoinButtonPressed}.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://atmapi.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RestService restService = retrofit.create(RestService.class);
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
    public interface OnJoinButtonPressed {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ATMEventViewModel item);
    }

    private final List<ATMEventViewModel> mValues;
    private final OnJoinButtonPressed mListener;

    public MyItemRecyclerViewAdapter(List<ATMEventViewModel> items, OnJoinButtonPressed listener) {
        mValues = items;
        mListener = listener;
    }

    public MyItemRecyclerViewAdapter(String item, OnJoinButtonPressed listener, int dummy) {
        Type listType = new TypeToken<ArrayList<EventFromBackend>>() {}.getType();
        Gson gson = new Gson();
        List<EventFromBackend> items = gson.fromJson(item, listType);

        mValues = convert(items);
        mListener = listener;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private List<ATMEventViewModel> convert(List<EventFromBackend> items) {
        List<ATMEventViewModel> result = new ArrayList<ATMEventViewModel>();
        for (EventFromBackend event : items) {
            long delta = new DateTime(event.getEnd_datetime()).getMillis() - new Date().getTime();
            int minutes = new DateTime(new Date(delta)).getMinuteOfHour();
            result.add(new ATMEventViewModel(event.get_id(),
                    event.getHashtag(),
                    event.getDescription(),
                    round(event.getDistance(), 2) + " KM", // returns 200.35event.getDistance() + " KM",
                    event.getParticipants_count(),
                    event.getHost_name(),
                    //todo timeleft,
                    minutes + " min",
                    "https://graph.facebook.com/" + event.getHost_id() + "/picture?width=128&height=128",
                    event.getImg_url()
                    ));
        }
        return result;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.descriptionText.setText(mValues.get(position).description);
        holder.titleText.setText(mValues.get(position).title);
        holder.distance.setText(mValues.get(position).distance);
        holder.numberOfAttendants.setText(Integer.toString(mValues.get(position).numberOfAttendants));
        holder.timeLeft.setText(mValues.get(position).timeLeft);
        holder.name.setText(mValues.get(position).hostName);
        holder.iminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Call<JoinEventBody> call = restService.joinEvent(mValues.get(position).id, new JoinEventBody((AccessToken.getCurrentAccessToken().getUserId())));
                call.enqueue(new Callback<JoinEventBody>() {
                    @Override
                    public void onResponse(Call<JoinEventBody> call, Response<JoinEventBody> response) {
                        mListener.onListFragmentInteraction(null);
                    }

                    @Override
                    public void onFailure(Call<JoinEventBody> call, Throwable t) {

                    }
                });
            }
        });
        loadImage(holder.eventPhoto, mValues.get(position).eventPhotoUrl);
        loadImage(holder.hostPhoto, mValues.get(position).hosterPhotoUrl);
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
        public final Button iminButton;

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
            iminButton = (Button) view.findViewById(R.id.button_join);
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
            if (result != null) {
                bmImage.setImageBitmap(result);
            }
        }
    }

}
