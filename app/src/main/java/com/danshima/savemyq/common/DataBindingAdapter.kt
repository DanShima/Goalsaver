package com.danshima.savemyq.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.danshima.savemyq.R
import com.danshima.savemyq.model.SavingsGoal
import com.danshima.savemyq.model.SavingsRule
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .fallback(R.drawable.ic_icon_1) //random image
            .error(ColorDrawable(Color.RED)) //random error image
            .into(view)
    }
}


@BindingAdapter("setGoalText")
fun bindSetGoalText(view: TextView, goal: SavingsGoal) {
    goal.targetAmount?.let {
        view.text = view.context.getString(
            R.string.out_of_total,
            (goal.currentBalance).toInt().toString(),
            (goal.targetAmount).toInt().toString()
        )
    } ?: run {
        view.text = view.context.getString(R.string.null_target, (goal.currentBalance).toInt().toString())
    }
}


@BindingAdapter("bindAmount")
fun bindAmount(view: TextView, amount: Float?) {
    val formatCurrencySymbol = NumberFormat.getCurrencyInstance(Locale.getDefault())
    formatCurrencySymbol.currency = Currency.getInstance("USD")
    view.text = formatCurrencySymbol.format(amount ?: 0.0f)
}


@BindingAdapter("bindHtmlString")
fun bindHtmlString(view: TextView, htmlString: String) {
    view.text = HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_COMPACT)
}


@BindingAdapter("bindProgressBarGoal")
fun bindProgressBarGoal(progressBar: ProgressBar, goal: SavingsGoal) {
    if (goal.targetAmount != null) {
        progressBar.max = goal.targetAmount.toInt()
        progressBar.progress = goal.currentBalance.toInt()
    } else {
        progressBar.visibility = View.GONE
    }
}

@BindingAdapter("bindTimestamp")
fun bindTimestamp(textView: TextView, timestamp: String) {
    textView.text = SimpleDateFormat(timestamp, Locale.US).toString()
}

@BindingAdapter("bindFeedIcon")
fun bindFeedIcon(imageView: ImageView, feedType: String) {
    val drawable = when (feedType) {
        "saving" -> {
            R.drawable.ic_money
        }
        else -> {
            R.drawable.ic_round_up
        }
    }
    imageView.setImageDrawable(VectorDrawableCompat.create(imageView.resources, drawable, null))
}

@BindingAdapter("bindSavingRulesIcon")
fun bindSavingRulesIcon(imageView: ImageView, savingsRule: SavingsRule) {
    val drawable = when {
        savingsRule.type == "roundup" -> {
            R.drawable.ic_round_up
        }
        savingsRule.type == "guilty_pleasure" -> {
            R.drawable.ic_icon_1
        }
        else -> {
            R.drawable.ic_round_up
        }
    }
    imageView.setImageResource(drawable)
}