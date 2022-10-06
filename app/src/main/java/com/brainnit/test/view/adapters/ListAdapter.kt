package com.brainnit.test.view.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brainnit.test.R
import com.facebook.drawee.view.SimpleDraweeView
import com.brainnit.test.model.ResponseModelItem as ResponseModelItem

class ListAdapter(
    private val context: Context,
    private var mediasList: ArrayList<ResponseModelItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoadingAdded = false
    private val item: Int = 0
    private val loading: Int = 1

    /*
    * creating viewholder based on view type
    * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return  if(viewType == item){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            MediaViewHolder(view)
        }else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            LoaderViewHolder(view)
        }
    }


    /*
    * Binding views to particular viewholder
    * */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val ResponseModelItem = mediasList[position]
        when(holder) {
            is MediaViewHolder -> {
                val mediaVH: MediaViewHolder = holder as MediaViewHolder
                if(ResponseModelItem.source_url!=null) {
                    mediaVH.image.setImageURI(Uri.parse(ResponseModelItem.source_url));
                }
            }
            else -> {
                val LoadingVH: LoaderViewHolder = holder as LoaderViewHolder
                LoadingVH.loadingProgress.visibility = View.VISIBLE
            }
        }
    }

    fun add(movie: ResponseModelItem) {
        mediasList.add(movie)
        notifyItemInserted(mediasList.size - 1)
    }

    fun addAll(mediaResults: List<ResponseModelItem>) {
        for (result in mediaResults) {
            add(result)
        }
    }

    override fun getItemCount(): Int {
        return if (mediasList.size > 0) mediasList.size else 0
    }

    /*
    * Loading footer view
    * adding empty model to last position of array for showing progressbar
    * */
    fun isLoadingFooter() {
        isLoadingAdded = true
        val user = ResponseModelItem()
        add(user)
    }

    /*
    * Removing last loaded progressbar after inserting new data to the list
    * */
    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position: Int =mediasList.size -1
        val medias: ResponseModelItem = mediasList[position]

        if(medias != null){
            mediasList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    /*
    * For getting viewtypes based on the position
    * */
    override fun getItemViewType(position: Int): Int {
        return if(position == 0){
            item
        }else {
            if (position == mediasList.size -1  && isLoadingAdded) {
                loading
            } else {
                item
            }
        }
    }


    /*
    * viewholder for list items
    * image
    * title
    * */
    class MediaViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
        val textView: TextView = itemView.findViewById(R.id.name)
        val image: SimpleDraweeView = itemView.findViewById(R.id.image)
    }


    /*
    * viewholder  for progress loader
    * */
    class LoaderViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
        val loadingProgress: ProgressBar = itemView.findViewById(R.id.loadmore_progress)
    }

}