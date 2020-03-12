package br.com.lsm.androidsample.presentation.githubList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.lsm.androidsample.R
import br.com.lsm.androidsample.presentation.vo.LanguageViewObject
import kotlinx.android.synthetic.main.item_language.view.*

class LanguagesAdapter(
    private val data: List<LanguageViewObject>,
    private val onItemClick: (LanguageViewObject) -> Unit
) : RecyclerView.Adapter<LanguagesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_language,
                parent,
                false
            ), onItemClick
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(
        view: View,
        private val onItemClick: (LanguageViewObject) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: LanguageViewObject) {
            itemView.imgLogo?.setImageResource(item.imageResId)
            itemView.txtName?.text = itemView.context.getString(item.displayNameResId)
            itemView.setOnClickListener { onItemClick.invoke(item) }

            itemView.rootViewGroup?.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    if (item.isSelected) R.color.white else R.color.black
                )
            )

            itemView.txtName?.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    if (item.isSelected) R.color.black else R.color.white
                )
            )
        }
    }
}