package app.fujii.iropic.color2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.SimpleDateFormat
import java.util.*


class TaskAdapter(
    private val context: Context,
    private var taskList: OrderedRealmCollection<Task>?,
    private var listener: OnItemClickListener, // ---------追加----------
    private val autoUpdate: Boolean
) :
    RealmRecyclerViewAdapter<Task, TaskAdapter.TaskViewHolder>(taskList, autoUpdate) {

    override fun getItemCount(): Int = taskList?.size ?: 0

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task: Task = taskList?.get(position) ?: return

        // ----------------追加---------------
        holder.container.setOnClickListener{
            listener.onItemClick(task)
        }
        // -----------------------------------
        holder.imageView.setImageResource(task.imageId)
        holder.contentTextView.text = task.content
        holder.dateTextView.text =
            SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPANESE).format(task.createdAt)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TaskViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false)
        return TaskViewHolder(v)
    }

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container : LinearLayout = view.container  // ---------追加----------
        val imageView: ImageView = view.imageView
        val contentTextView: TextView = view.contentTextView
        val dateTextView: TextView = view.dateTextView
    }

    // ----------------追加---------------
    interface OnItemClickListener {
        fun onItemClick(item: Task)
    }
    // -----------------------------------
}