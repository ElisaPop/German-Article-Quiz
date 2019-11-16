package android.example.germanarticlequiz

import android.content.Context
import android.example.germanarticlequiz.databinding.FragmentGameBinding
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.Gravity.BOTTOM
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [GameFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class GameFragment : Fragment() {
    data class Question(
        val text: String,
        val answers: List<String>)

    // The first answer is the correct one.  We randomize the answers before showing the text.
    // All questions must have four answers.  We'd want these to contain references to string
    // resources so we could internationalize. (or better yet, not define the questions in code...)
    private val questions: MutableList<Question> = mutableListOf(
        Question(text = "Nouns ending in -eur",
            answers = listOf("der", "die", "das")),
        Question(text = "Nouns ending in -ant",
            answers = listOf("der", "die", "das")),
        Question(text = "Nouns ending in -ent",
            answers = listOf("der", "die", "das")),
        Question(text = "Nouns ending in -ar",
            answers = listOf("der", "die", "das")),
        Question(text = "Nouns ending in -är",
            answers = listOf("der", "die", "das")),
        Question(text = "Nouns ending in -ismus",
            answers = listOf("der", "die", "das")),
        Question(text = "Nouns ending in -ist",
            answers = listOf("der", "die", "das")),
        Question(text = "Nouns ending in -ast",
            answers = listOf("der", "die", "das")),
        Question(text = "Nouns ending in -ig",
            answers = listOf("der", "die", "das")),
        Question(text = "Nouns ending in -ling",
            answers = listOf("der", "die", "das")),
        Question(text = "Nouns ending in -or",
            answers = listOf("der", "die", "das")),
        Question(text = "Nouns ending in -us",
            answers = listOf("der", "die", "das")),

        Question(text = "60% of Nouns ending in -el",
            answers = listOf("der", "die", "das")),
        Question(text = "60% of Nouns ending in -er",
            answers = listOf("der", "die", "das")),
        Question(text = "80% of Nouns ending in -en",
            answers = listOf("der", "die", "das")),


        Question(text = "Nouns ending in -a",
            answers = listOf("die", "der", "das")),
        Question(text = "Nouns ending in -anz",
            answers = listOf("die", "der", "das")),
        Question(text = "Nouns ending in -enz",
            answers = listOf("die", "der", "das")),
        Question(text = "Nouns ending in -ei",
            answers = listOf("die", "der", "das")),
        Question(text = "Nouns ending in -ie",
            answers = listOf("die", "der", "das")),
        Question(text = "Nouns ending in -ik",
            answers = listOf("die", "der", "das")),
        Question(text = "Nouns ending in -in",
            answers = listOf("die", "der", "das")),
        Question(text = "Nouns ending in -heit",
            answers = listOf("die", "der", "das")),
        Question(text = "Nouns ending in -keit",
            answers = listOf("die", "der", "das")),
        Question(text = "Nouns ending in -schaft",
            answers = listOf("die", "der", "das")),
        Question(text = "Nouns ending in -tät",
            answers = listOf("die", "der", "das")),
        Question(text = "Nouns ending in -sion",
            answers = listOf("die", "der", "das")),
        Question(text = "Nouns ending in -tion",
            answers = listOf("die", "der", "das")),
        Question(text = "Nouns ending in -ion",
            answers = listOf("die", "der", "das")),
        Question(text = "Nouns ending in -und",
            answers = listOf("die", "der", "das")),
        Question(text = "Nouns ending in -ung",
            answers = listOf("die", "der", "das")),
        Question(text = "Nouns ending in -ur",
            answers = listOf("die", "der", "das")),
        Question(text = "Nouns ending in -itis",
            answers = listOf("die", "der", "das")),
        Question(text = "Nouns ending in -age",
            answers = listOf("die", "der", "das")),

        Question(text = "90% of Nouns ending in -e",
            answers = listOf("die", "der", "das")),



        Question(text = "Nouns ending in -chen",
            answers = listOf("das", "der", "die")),
        Question(text = "Nouns ending in -lein",
            answers = listOf("das", "der", "die")),
        Question(text = "Nouns ending in -le",
            answers = listOf("das", "der", "die")),
        Question(text = "Nouns ending in -sel",
            answers = listOf("das", "der", "die")),
        Question(text = "Nouns ending in -tel",
            answers = listOf("das", "der", "die")),
        Question(text = "Nouns ending in -tum",
            answers = listOf("das", "der", "die")),
        Question(text = "Nouns ending in -um",
            answers = listOf("das", "der", "die")),
        Question(text = "Nouns ending in -ma",
            answers = listOf("das", "der", "die")),
        Question(text = "Nouns ending in -ment",
            answers = listOf("das", "der", "die")),

        Question(text = "90% of Nouns starting with Ge-",
            answers = listOf("das", "der", "die")),
        Question(text = "60% of Nouns ending in -nis",
            answers = listOf("das", "der", "die")),
        Question(text = "60% of Nouns ending in -sal",
            answers = listOf("das", "der", "die"))
    )

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 10)
    private var nrCorrectAnswers = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
            inflater, R.layout.fragment_game, container, false)

        // Shuffles the questions and sets the question index to the first question.
        randomizeQuestions()

        // Bind this fragment class to the layout
        binding.game = this

        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                }
                // If the answer is correct, increase the counter of correct answers
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    nrCorrectAnswers++
                }
                // If the answer is wrong, send a toast with the correct message and continue
                else{
                    var toast: Toast = Toast.makeText(context, "It was ${currentQuestion.answers[0]}!" , Toast.LENGTH_SHORT)//.show()
                    toast.setGravity(BOTTOM,0,10)
                    toast.show()
                }
                questionIndex++
                // Advance to the next question
                if (questionIndex < numQuestions) {
                    currentQuestion = questions[questionIndex]

                    setQuestion()
                    binding.invalidateAll()
                } else {
                    if(nrCorrectAnswers == questionIndex){
                        // We've won!  Navigate to the gameWonFragment.
                        view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(nrCorrectAnswers,numQuestions))
                    } else {
                        // Game over!
                        view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment2(nrCorrectAnswers,numQuestions))
                    }
                }
            }

        }
        return binding.root
    }

    // randomize the questions and set the first question
    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)
    }
    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GameFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
