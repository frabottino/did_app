package it.polito.did.gameskeleton

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EmojiViewModel : ViewModel() {
    private val emojis: MutableLiveData<MutableList<EmojiModel>> by lazy {
        MutableLiveData<MutableList<EmojiModel>>()
    }

    var isFinished = false
    var pts = 30



    var timer = object: CountDownTimer(30000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            pts = (millisUntilFinished/1000).toInt()
        }
        override fun onFinish() {
            if(!isFinished) {
                GameViewModel.getInstance().sendMiniPts(0)
            }
        }
    }

    fun getEmojis(): LiveData<MutableList<EmojiModel>> {
        return emojis
    }

    fun loadEmojis() {
        emojis.value = mutableListOf(
            EmojiModel("\uD83C\uDF33"), //albero
            EmojiModel("\uD83C\uDF33"), //albero
           /* EmojiModel("\uD83C\uDF3C"), //fiore
            EmojiModel("\uD83C\uDF3C"), //fiore
            EmojiModel("\uD83C\uDF0D"), //pianeta
            EmojiModel("\uD83C\uDF0D"), //pianeta
            EmojiModel("\uD83D\uDCA7"), //acqua
            EmojiModel("\uD83D\uDCA7"), //acqua
            EmojiModel("☀"), //sole
            EmojiModel("☀"), //sole
            EmojiModel("⚡"), //fulmine
            EmojiModel("⚡"), //fulmine

            */
        ).apply { shuffle() }
        timer.start()
    }

    fun updateShowVisibleCard(
        id: String,
        msn: MutableLiveData<ScreenName>,
        vm: GameViewModel
    ) {

        val selects: List<EmojiModel>? = emojis.value?.filter { it -> it.isSelect }
        val selectCount: Int = selects?.size ?: 0
        var charFind: String = "";
        if (selectCount >= 2) {
            val hasSameChar: Boolean = selects!!.get(0).char == selects.get(1).char
            if (hasSameChar) {
                charFind = selects.get(0).char
            }
        }

        val list: MutableList<EmojiModel>? = emojis.value?.map { it ->
            if (selectCount >= 2) {
                it.isSelect = false
            }

            if (it.char == charFind) {
                it.isVisible = false
            }

            if (it.id == id) {
                it.isSelect = true
            }

            it
        } as MutableList<EmojiModel>?

        val visibleCount: Int = list?.filter { it -> it.isVisible }?.size ?: 0
        if (visibleCount <= 0) {
            timer.cancel()
            isFinished = true
            vm.nextTurn()
            vm.sendMiniPts(pts)
            return
        }

        emojis.value?.removeAll { true }
        emojis.value = list
    }
}