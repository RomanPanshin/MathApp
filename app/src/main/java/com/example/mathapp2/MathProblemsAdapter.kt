package com.example.mathapp2
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MathProblemsAdapter : RecyclerView.Adapter<MathProblemsAdapter.ProblemViewHolder>() {

    private var problems: List<String> = listOf()

    class ProblemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val problemTextView: TextView = view.findViewById(R.id.problem_text)

        fun bind(problem: String) {
            problemTextView.text = problem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_math_problem, parent, false)
        return ProblemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProblemViewHolder, position: Int) {
        holder.bind(problems[position])
    }

    override fun getItemCount() = problems.size

    fun setProblems(newProblems: List<String>) {
        problems = newProblems
        notifyDataSetChanged()
    }
}
