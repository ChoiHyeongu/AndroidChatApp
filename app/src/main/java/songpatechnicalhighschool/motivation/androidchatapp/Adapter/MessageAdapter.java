package songpatechnicalhighschool.motivation.androidchatapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import songpatechnicalhighschool.motivation.androidchatapp.Model.Chat;
import songpatechnicalhighschool.motivation.androidchatapp.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private boolean isVisible = false;

    private Context mContext;
    private List<Chat> mChat;
    private String imageurl;

    FirebaseUser firebaseUser;

    public MessageAdapter(Context mContext, List<Chat> mChat, String imageurl) {
        this.mContext = mContext;
        this.mChat = mChat;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Chat chat = mChat.get(position);

        holder.show_message.setText(chat.getMessage());
        holder.show_time.setText(chat.getSendTime());

        if (imageurl.equals("default")) {
            holder.profile_Image.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext)
                    .load(imageurl)
                    .into(holder.profile_Image);
        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView show_message;
        public ImageView profile_Image;
        public TextView show_time;

        public ViewHolder(View itemView) {
            super(itemView);

            show_time = itemView.findViewById(R.id.show_time);
            show_message = itemView.findViewById(R.id.show_message);
            profile_Image = itemView.findViewById(R.id.profile_image);

            show_message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(mContext, ""+isVisible, Toast.LENGTH_SHORT).show();

                    if(isVisible) {
                        show_time.setVisibility(View.GONE);
                        isVisible = !isVisible;
                    } else {
                        show_time.setVisibility(View.VISIBLE);
                        isVisible = !isVisible;
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(firebaseUser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
