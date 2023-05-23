package it.polito.did.gameskeleton

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class GameManager(private val scope:CoroutineScope) {
    private val URL = "https://didb-2a4ab-default-rtdb.firebaseio.com/"
    private val firebase = Firebase.database(URL)
    private val firebaseAuth = Firebase.auth
    private var playerName = String()
    private var currentTeam = Team()
    private var currentPlayer = Player()
    private var player = Player()
    private var gameID : Int = 0
    private var yMiniPts : Int = 0
    private var gMiniPts : Int = 0
    private var rMiniPts : Int = 0
    private var bMiniPts : Int = 0
    private var rCards = ArrayList<Int>()
    private var bCards = ArrayList<Int>()
    private var gCards = ArrayList<Int>()
    private var yCards = ArrayList<Int>()
    private var rDeck = ArrayList<Int>(7)
    private var bDeck = ArrayList<Int>(7)
    private var gDeck = ArrayList<Int>(7)
    private var yDeck = ArrayList<Int>(7)
    private var myDeck = ArrayList<Int>(7)
    private var myCards = ArrayList<Int>()
    private var rVictory : Int = 0
    private var bVictory : Int = 0
    private var gVictory : Int = 0
    private var yVictory : Int = 0
    private var myVictory : Int = 0
    private val teamNames = mutableListOf("Red", "Blue", "Green", "Yellow")
    private var sizes = ArrayList<Int>()
    private var turn : Int = 0
    private var phase : Int = 0
    private var capId : Int = 0
    private var card1 : Int = -1
    private var card2 : Int = -1
    private var card3 : Int = -1
    private var card4 : Int = -1
    private var card5 : Int = -1
    private var ranking = mutableListOf(rVictory, bVictory, gVictory, yVictory)
    private var discarded : Boolean = false


    init {
        //firebase.setLogLevel(Logger.Level.DEBUG)
        scope.launch {
            try {
                firebaseAuth.signInAnonymously().await()
                Log.d("GameManager", "Current User: ${firebaseAuth.uid}")
                delay(2000)
                mutableScreenName.value = ScreenName.Initial
            } catch (e: Exception) {
                mutableScreenName.value = ScreenName.Error(e.message?:"Unknown error")
            }
        }
    }

    private val mutableScreenName = MutableLiveData<ScreenName>().also {
        it.value = ScreenName.Splash
    }
    val screenName: LiveData<ScreenName> = mutableScreenName

    private val mutableMatchId = MutableLiveData<String>()
    val matchId: LiveData<String> = mutableMatchId

    private val mutablePlayers = MutableLiveData<Map<String, String>>().also {
        it.value = emptyMap()
        val teams : Teams
    }
    val players: LiveData<Map<String, String>> = mutablePlayers

    private val emojis: MutableLiveData<MutableList<EmojiModel>> by lazy {
        MutableLiveData<MutableList<EmojiModel>>()
    }

    private fun assignTeam(players: Map<String,String>): Map<String,String>? {
        val teams = players.keys.groupBy { players[it].toString() } //raggruppa i giocatori per squadra
        //println("id ${players.keys.size}")
        sizes = teamNames.map{ teams[it]?.size ?: 0 } as ArrayList<Int> //guarda il numero di componenti per ogni squadra
        val pickAGuy = teamNames.map{ teams[it]?.get(0)?: "" } //piglia il quartetto di giocatori che sono nell'indice x della squadra
        val min: Int = sizes.stream().min(Integer::compare).get() //guarda il valore più basso di componenti in una squadra
        //println("min $min")
        //println("size1 $sizes")
        var index = sizes.indexOf(min) //indica l'indice della squadra con meno giocatori
        //println("team ${teamNames[index]}")
        val updatedPlayers = players.toMutableMap() //lista dei giocatori con squadra
        var changed = false
        //println("players $updatedPlayers")
        //println("the guy is $pickAGuy")
        //println("the captain is ${players.keys}")
        player.setPlayerId(min)
        if(index!=0) player.setPlayerTeam(index-1)
        else player.setPlayerTeam(3)
        //println("player id ${player.getPlayerId()}, team ${player.getPlayerTeam()}")
        if(player.getPlayerId() != -1) getDatabase(teamNames[index])
        teams[""]?.forEach {
            updatedPlayers[it] = teamNames[index] //lista giocatori aggiornata
            index = (index +1) % teamNames.size //aggiorna indice di squadra meno popolata
            changed = true
        }
        return if (changed) updatedPlayers else null
    }

    private fun watchPlayers() {
        val id = matchId.value ?: throw RuntimeException("Missing match Id")
        val ref = firebase.getReference(id)
        ref.child("players").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val v = snapshot.value
                if (v!=null && v is Map<*, *>) {
                    val updatedPlayers = assignTeam(v as Map<String,String>)
                    if (updatedPlayers != null) {
                        ref.child("players").setValue(updatedPlayers)
                    } else {
                        mutablePlayers.value = v
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

    }

    private fun getMyTeam(): String {
        Log.d("GameManager", "players: ${players.value}")
        Log.d("GameManager", "uid: ${firebaseAuth.uid}")
        //println("ciao ${players.value?.toString()}")
        //println("${firebaseAuth.uid}")
        //println(playerName)
        return players.value?.get(playerName) ?: ""
        //return players.value?.get(firebaseAuth.uid) ?: ""
    }

    private fun getMyName(): String {
        Log.d("GameManager", "players: ${players.value}")
        Log.d("GameManager", "uid: ${firebaseAuth.uid}")
        return players.value?.get(firebaseAuth.toString()) ?: ""
    }

    fun createNewGame() {
        scope.launch {
            try {
                val random = Random().nextInt( 89999) + 10000
                val ref = firebase.getReference(random.toString())
                gameID = random
                //val ref = firebase.reference.push()
                Log.d("GameManager","Creating match ${ref.key}")
                ref.setValue(
                    mapOf(
                        "date" to LocalDateTime.now().toString(),
                        "owner" to firebaseAuth.uid,
                        "screen" to "WaitingStart"
                    )
                ).await()
                setDatabase(ref)
                Log.d("GameManager", "Match creation succeeded")
                mutableMatchId.value = ref.key
                mutableScreenName.value = ScreenName.SetupMatch(ref.key!!)
                watchPlayers()
            } catch (e:Exception) {
                mutableScreenName.value = ScreenName.Error(e.message ?: "Generic error")
            }
        }
    }

    private fun watchScreen() {
        val id = matchId.value ?: throw RuntimeException("Missing match Id")
        val ref = firebase.getReference(id)
        ref.child("screen").addValueEventListener(
            object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    mutableScreenName.value = getScreenName(snapshot.value?.toString()?: "")
                }

                override fun onCancelled(error: DatabaseError) {
                    mutableScreenName.value = ScreenName.Error(error.message)
                }
            }
        )

    }

    private fun getScreenName(name:String): ScreenName {
        return when (name) {
            "WaitingStart" -> ScreenName.WaitingStart
            "Playing" -> ScreenName.Home(getMyTeam()) //qui è dove si indica il primo screen del player
            //"Playing" -> ScreenName.Menu(getMyTeam()) //qui è dove si indica il primo screen del player
            else -> ScreenName.Error("Unknown screen $name")
        }
    }

    fun joinGame(matchId:String, nameId:String) {
        if (matchId.isEmpty()) return
        playerName = nameId
        scope.launch {
            try {
                val ref = firebase.getReference(matchId)
                val data = ref.get().await()
                if (data!=null) {
                    mutableMatchId.value = matchId
                    gameID = matchId.toInt()
                    ref
                        .child("players")
                        .child(playerName)//firebaseAuth.uid!!)
                        .setValue("").await()
                    watchPlayers()
                    watchScreen()
                } else {
                    mutableScreenName.value = ScreenName.Error("Invalid gameId")
                }
            } catch (e: Exception) {
                mutableScreenName.value = ScreenName.Error(e.message?: "Generic error")
            }
        }
    }

    fun startGame() {
        scope.launch{
            try {
                val ref = firebase.getReference(matchId.value ?: throw RuntimeException("Invalid State"))
                ref.child("screen").setValue("Playing").await()
                mutableScreenName.value = ScreenName.Generic
                player
                Log.d("GameManager", "Game started")
            } catch (e: Exception) {
                mutableScreenName.value = ScreenName.Error(e.message ?: "Generic error")
            }
        }
        player.setPlayerId(-1)
    }

    fun goToHome(){
        mutableScreenName.value = ScreenName.Home(getMyTeam())
        //TODO try catch da mettere a posto
    }

    fun goToMascotte(){
        mutableScreenName.value = ScreenName.Mascotte(getMyTeam())
        //TODO try catch da mettere a posto
    }

    fun getDatabase(myTeam: String) {
        val ref = firebase.getReference(gameID.toString())
        println("get database playerid ${player.getPlayerId()}, ${gameID.toString()}, ${myTeam}")
        var phaseRef = ref.child("Phase").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //println("onDataChange with ${snapshot.value}")
                if(snapshot.value.toString() != "0"){
                    endPhase()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        var turnRef = ref.child("Turn").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                turn = Integer.valueOf(snapshot.value.toString())
                if((turn - 1)==player.getPlayerTeam())
                    goToMascotte()
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        var capIdRef = ref.child("CaptainID").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                capId = Integer.valueOf(snapshot.value.toString())
                currentPlayer.setPlayerId(capId)
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        var capTeamRef = ref.child("CaptainTeam").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("hello with ${snapshot.value}")
                currentPlayer.setPlayerTeam(Integer.valueOf(snapshot.value.toString()))
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        var cardsRef1 = ref.child("5Cards").child("1").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //println("card1 changed ${snapshot.value}")
                card1 = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

         var cardsRef2 = ref.child("5Cards").child("2").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //println("card2 changed ${snapshot.value}")
                card2 = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        var cardsRef3 = ref.child("5Cards").child("3").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //println("card3 changed ${snapshot.value}")
                card3 = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        var cardsRef4 = ref.child("5Cards").child("4").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               // println("card4 changed ${snapshot.value}")
                card4 = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        var cardsRef5 = ref.child("5Cards").child("5").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               // println("card5 changed ${snapshot.value}")
                card5 = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        var myTeamCards = ref.child(myTeam).child("Cards").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               // println("my team cards changed ${snapshot.value}, I'm $myTeam")
                if (snapshot.value.toString() != "0") {
                    var arr = snapshot.value.toString().drop(1).split(" ").toTypedArray()
                    for (i in arr.indices) {
                        arr[i] = arr[i].takeWhile { it.isDigit() }
                        println("arri my ${arr[i]}")
                        if(!myCards.contains(Integer.valueOf(arr[i])) && Integer.valueOf(arr[i]) != 88){
                            myCards.add(Integer.valueOf(arr[i]))
                            uploadDeck(Integer.valueOf(arr[i]), "My")
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        var myTeamVictory = ref.child(myTeam).child("VictoryPoints").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("my team victory changed ${snapshot.value}, I'm $myTeam")
                myVictory = Integer.valueOf(snapshot.value.toString())
                    }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

    }

    fun setDatabase(ref : DatabaseReference){
        val red = ref.child("Red")
        val blue = ref.child("Blue")
        val yellow = ref.child("Yellow")
        val green = ref.child("Green")
        val turnRef = ref.child("Turn")
        val capTeamRef = ref.child("CaptainTeam")
        val capIdRef = ref.child("CaptainID")
        val phaseRef = ref.child("Phase")
        val gMiniPoints = green.child("MiniPoints")
        val rMiniPoints = red.child("MiniPoints")
        val bMiniPoints = blue.child("MiniPoints")
        val yMiniPoints = yellow.child("MiniPoints")

        phaseRef.setValue(0)
        turnRef.setValue(1)
        capIdRef.setValue(0)
        capTeamRef.setValue(0)

        ConstantCards.shuffleInstantCards()
        ConstantCards.shufflePermanentCards()
        ConstantCards.getCardList1()
        setCards(ref)

        var discard1 = ref.child("Discard1").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               // println("discarded1 cards $discarded")
                if(snapshot.value != null)
                    ConstantCards.pickCard(Integer.valueOf(snapshot.value.toString()), "uno")
                if(discarded)
                    setCards(ref)
                discarded = !discarded
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        var discard2 = ref.child("Discard2").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
              //  println("discarded2 cards $discarded")
                if(snapshot.value != null)
                    ConstantCards.pickCard(Integer.valueOf(snapshot.value.toString()), "due")
                if(discarded)
                    setCards(ref)
                discarded = !discarded
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        phaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               // println("onDataChange with ${snapshot.value}")
                if(snapshot.value.toString() != "0"){
                    endPhase()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        turnRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //println("turn changed")
                turn = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        capIdRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                capId = Integer.valueOf(snapshot.value.toString())
                currentPlayer.setPlayerId(capId)
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        capTeamRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               // println("hello with ${snapshot.value}")
                currentPlayer.setPlayerTeam(Integer.valueOf(snapshot.value.toString()))
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        yMiniPoints.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               // println("yellow minipts changed ${snapshot.value}")
                val v = snapshot.value
                if (v!=null && v is Map<*, *>) yMiniPts = addYellowPoints(v as Map<String,Int>)
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        bMiniPoints.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               // println("blue minipts changed ${snapshot.value}")
                val v = snapshot.value
                if (v!=null && v is Map<*, *>) bMiniPts = addBluePoints(v as Map<String,Int>)
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        rMiniPoints.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               // println("red minipts changed ${snapshot.value}")
                val v = snapshot.value
                if (v!=null && v is Map<*, *>) rMiniPts = addRedPoints(v as Map<String,Int>)
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        gMiniPoints.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               // println("green minipts changed ${snapshot.value}")
                val v = snapshot.value
                if (v!=null && v is Map<*, *>) gMiniPts = addGreenPoints(v as Map<String,Int>)
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        var cardsRef1 = ref.child("5Cards").child("1").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               // println("card1 changed ${snapshot.value}")
                card1 = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        var cardsRef2 = ref.child("5Cards").child("2").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               // println("cards changed ${snapshot.value}")
                card2 = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        var cardsRef3 = ref.child("5Cards").child("3").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               // println("cards changed ${snapshot.value}")
                card3 = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        var cardsRef4 = ref.child("5Cards").child("4").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               // println("cards changed ${snapshot.value}")
                card4 = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        var cardsRef5 = ref.child("5Cards").child("5").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               // println("cards changed ${snapshot.value}")
                card5 = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        var blueCards = blue.child("Cards").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("blue cards changed ${snapshot.value}")
                if (snapshot.value.toString() != "0") {
                    var arr = snapshot.value.toString().drop(1).split(" ").toTypedArray()
                    for (i in arr.indices) {
                        arr[i] = arr[i].takeWhile { it.isDigit() }
                        println("arri ${arr[i]}")
                        if(!bCards.contains(Integer.valueOf(arr[i])) && Integer.valueOf(arr[i]) != 88){
                            bCards.add(Integer.valueOf(arr[i]))
                            uploadDeck(Integer.valueOf(arr[i]), "Blue")
                        }
                    }
                    //if(bCards.size % 2 != 1) doTheMath(bCards, "Blue")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })


        var redCards = red.child("Cards").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("red cards changed ${snapshot.value}")
                if (snapshot.value.toString() != "0") {
                    var arr = snapshot.value.toString().drop(1).split(" ").toTypedArray()
                    for (i in arr.indices) {
                        arr[i] = arr[i].takeWhile { it.isDigit() }
                        println("arri ${arr[i]}")
                        if(!rCards.contains(Integer.valueOf(arr[i])) && Integer.valueOf(arr[i]) != 88){
                            rCards.add(Integer.valueOf(arr[i]))
                            uploadDeck(Integer.valueOf(arr[i]), "Red")
                        }
                    }
                    //if(rCards.size % 2 != 1) doTheMath(rCards, "Red")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })


        var greenCards = green.child("Cards").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("green cards changed ${snapshot.value}")
                if (snapshot.value.toString() != "0") {
                    var arr = snapshot.value.toString().drop(1).split(" ").toTypedArray()
                    for (i in arr.indices) {
                        arr[i] = arr[i].takeWhile { it.isDigit() }
                        println("arri ${arr[i]}")
                        if(!gCards.contains(Integer.valueOf(arr[i])) && Integer.valueOf(arr[i]) != 88){
                            gCards.add(Integer.valueOf(arr[i]))
                            uploadDeck(Integer.valueOf(arr[i]), "Green")
                        }
                    }
                    //if(gCards.size % 2 != 1) doTheMath(gCards, "Green")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })


        var yellowCards = yellow.child("Cards").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("yellow cards changed ${snapshot.value}")
                if (snapshot.value.toString() != "0") {
                    var arr = snapshot.value.toString().drop(1).split(" ").toTypedArray()
                    for (i in arr.indices) {
                        arr[i] = arr[i].takeWhile { it.isDigit() }
                        println("arri ${arr[i]}")
                        if(!yCards.contains(Integer.valueOf(arr[i])) && Integer.valueOf(arr[i]) != 88){
                            yCards.add(Integer.valueOf(arr[i]))
                            uploadDeck(Integer.valueOf(arr[i]), "Yellow")
                        }
                    }
                    //if(yCards.size % 2 != 1) doTheMath(yCards, "Yellow")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        var redVictory = red.child("VictoryPoints").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("red victory changed ${snapshot.value}")
                rVictory = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        var blueVictory = blue.child("VictoryPoints").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("blue victory changed ${snapshot.value}")
                bVictory = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        var greenVictory = green.child("VictoryPoints").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("green victory changed ${snapshot.value}")
                gVictory = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        var yellowVictory = yellow.child("VictoryPoints").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("yellow victory changed ${snapshot.value}")
                yVictory = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })


        red.setValue(
            mapOf(
                "MiniPoints" to 0,
                "VictoryPoints" to 0,
                "Cards" to 0,
                "Money" to 0,
                "Energy" to 0
            )
        )

        blue.setValue(
            mapOf(
                "MiniPoints" to 0,
                "VictoryPoints" to 0,
                "Cards" to 0,
                "Money" to 0,
                "Energy" to 0
            )
        )
        yellow.setValue(
            mapOf(
                "MiniPoints" to 0,
                "VictoryPoints" to 0,
                "Cards" to 0,
                "Money" to 0,
                "Energy" to 0
            )
        )
        green.setValue(
            mapOf(
                "MiniPoints" to 0,
                "VictoryPoints" to 0,
                "Cards" to 0,
                "Money" to 0,
                "Energy" to 0
            )
        )
    }

    fun setCards(ref : DatabaseReference){
        var cards = ConstantCards.getCurrentList()
        selectFiveCards(cards, ref)
    }

    fun selectFiveCards(cards : ArrayList<Card>, ref : DatabaseReference){
        ref.child("5Cards").child("1").setValue(cards[0].idC)
        ref.child("5Cards").child("2").setValue(cards[1].idC)
        ref.child("5Cards").child("3").setValue(cards[2].idC)
        ref.child("5Cards").child("4").setValue(cards[3].idC)
        ref.child("5Cards").child("5").setValue(cards[4].idC)
    }

    fun addRedPoints(players: Map<String, Int>) : Int{
        val points = players.values.sum()
        //println("punti rossi $points")
        return points
    }

    fun addBluePoints(players: Map<String, Int>) : Int{
        val points = players.values.sum()
        //println("punti blu $points")
        return 0
    }

    fun addGreenPoints(players: Map<String, Int>) : Int{
        val points = players.values.sum()
       // println("punti verdi  $points")
        return points
    }

    fun addYellowPoints(players: Map<String, Int>) : Int{
        val points = players.values.sum()
        //println("punti gialli $points")
        return points
    }

    fun endTurn(teams : Teams){
        // TODO prendere riferimento di chi ha appena schiacciato il bottone di endturn
        //disabilitare modifiche alla sua squadra
        //cambiare il riferimento alla squadra in possesso del turno
        //avviare schermata mascotte passando il riferimento della squadra corrente
    }

    fun changeTurn(){
        var oldCapTeam = currentPlayer.getPlayerTeam()
        //println("old cap id $capId , team $oldCapTeam")
       // println("sizes $sizes")
        //println("game $gameID")

        oldCapTeam++
       // println("nuovo oldcapteam ${(oldCapTeam) % teamNames.size}")

        firebase.getReference(gameID.toString()).child("CaptainTeam").setValue((oldCapTeam) % teamNames.size)

        if(currentPlayer.getPlayerTeam() == 0){
            capId++
           // println("old turn $turn")
            if (turn++ == 4){
                turn = 1
                firebase.getReference(gameID.toString()).child("Phase").setValue(phase + 1)
            }
            firebase.getReference(gameID.toString()).child("CaptainID").setValue(capId)
            firebase.getReference(gameID.toString()).child("Turn").setValue(turn)
           // println("now turn $turn")
        }
        //currentPlayer.setPlayerId(capId % sizes[currentPlayer.getPlayerTeam()])
       // println("now id ${currentPlayer.getPlayerId()}, team ${currentPlayer.getPlayerTeam()}")
    }

    fun getTurn() : Int{
       // println("turno $turn, capID $capId")
        //return turn //TODO questo è quello giusto
        return (capId + 1)
    }

    fun endPhase() {
        val ref = firebase.getReference(gameID.toString())

        phase++
       // println("endphase here")
        ranking.sortDescending()
        //TODO separatore tra le fasi + calcolo classifica
    }

    fun startFlappy() {
        mutableScreenName.value = ScreenName.Flappy
        //TODO try catch da mettere a posto e impostare schermata solo per un team
    }

    fun startQuiz(){
        mutableScreenName.value = ScreenName.Quiz
        //TODO try catch da mettere a posto e impostare schermata solo per un team
    }

    fun isPlayerTurn() : Boolean{
        player.setYourTurn(capId)
        return player.isYourTurn()
    }

    fun isTeamTurn() : Boolean{
        player.setYourTeamTurn(turn)
        return player.isYourTeamTurn()
    }

    fun startMemory() {
        emojis.value = mutableListOf(
            EmojiModel("😍"),
            EmojiModel(" 🥰"),
            EmojiModel("😘"),
            EmojiModel("😭"),
            EmojiModel("😢"),
            EmojiModel("😂"),
            EmojiModel("😍"),
            EmojiModel(" 🥰"),
            EmojiModel("😘"),
            EmojiModel("😭"),
            EmojiModel("😢"),
            EmojiModel("😂"),
        ).apply { shuffle() }
        mutableScreenName.value = ScreenName.Memory
        //TODO try catch da mettere a posto
    }

    fun sendMiniResult(pts : Int) {
        //println("sendminiresult cooking")
        firebase.getReference(gameID.toString()).child(getMyTeam()).child("MiniPoints").child(playerName).setValue(pts)
        mutableScreenName.value = ScreenName.Cards(getMyTeam())
        //TODO calcolare e inviare al server il punteggio di squadra del minigame, mettere riferimento a squadra
    }

    fun pickCards(one: Int, two: Int, check1 : Int?, cardOne : Int?, check2 : Int?, cardTwo : Int?){
        var min: Int
        var max: Int

        if(cardOne != null && check1 != null)
            if(cardOne == 88) setVehicleFromUnexpected(one, check1)
            else if(cardOne in 82..85){
                setPenaltyFromUnexpected(one, cardOne, check1)
            }
        if(cardTwo != null && check2 != null)
            if(cardTwo == 88) setVehicleFromUnexpected(two, check2)
            else if(cardTwo in 82..85) {
                setPenaltyFromUnexpected(two, cardTwo, check2)
            }

        if(one < two) {
            max = two
            min = one
        }
        else {
            max = one
            min = two
        }
        if(max in 21..25 || min in 21..25) checkCard(1, "Energy")
        if(max in 26..30 || min in 26..30) checkCard(2, "Energy")
        if(max in 36..40 || min in 36..40) checkCard(1, "Money")
        if(max in 41..45 || min in 41..45) checkCard(2, "Money")
        when (max) {
            1 -> {
                firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                    .child(card1.toString()).setValue(card1)
            }
            2 -> {
                firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                    .child(card2.toString()).setValue(card2)
            }
            3 -> {
                firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                    .child(card3.toString()).setValue(card3)
            }
            4 -> {
                firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                    .child(card4.toString()).setValue(card4)
            }
            5 -> {
                firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                    .child(card5.toString()).setValue(card5)
            }
        }
        firebase.getReference(gameID.toString()).child("Discard1").setValue(max)
        when (min) {
            1 -> {
                firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                    .child(card1.toString()).setValue(card1)
            }
            2 -> {
                firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                    .child(card2.toString()).setValue(card2)
            }
            3 -> {
                firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                    .child(card3.toString()).setValue(card3)
            }
            4 -> {
                firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                    .child(card4.toString()).setValue(card4)
            }
            5 -> {
                firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                    .child(card5.toString()).setValue(card5)
            }
        }
        firebase.getReference(gameID.toString()).child("Discard2").setValue(min)
        goToHome()
    }

    fun sendFiveCards(): ArrayList<Int> {
        var cards = ArrayList<Int>()
        cards.add(card1)
        cards.add(card2)
        cards.add(card3)
        cards.add(card4)
        cards.add(card5)
        return cards
    }

    fun doTheMath(cards : ArrayList<Int>, team : String){ //TODO da rivedere e o eliminare
        var countT1 = 0
        var countT2 = 0
        var countT3 = 0
        var countP = 0
        var countE = 0
        var countM = 0
        var countH = -10
        var countI = 0
        var greenI = false
        for(i in 0 until cards.size){
           // println("doTheMath has ${cards[i]}")
            if(cards[i] <= 5 || cards[i] == 91) countT1++
            if(cards[i] in 6..10 || cards[i] == 92) countT2++
            if(cards[i] in 11..15 || cards[i] == 93) countT3++
            if(cards[i] in 16..20) countP++
            if(cards[i] in 21..25) countP+=2
            if(cards[i] in 26..30) countP+=5
            if(cards[i] in 31..35) countH++
            if(cards[i] in 36..40) countH+=3
            if(cards[i] in 41..45) countH+=7
            if(cards[i] in 46..51) countM++
            if(cards[i] in 52..56) countM+=2
            if(cards[i] in 57..60) countM+=3
            if(cards[i] in 61..63) countM+=4
            if(cards[i] in 64..69) countE++
            if(cards[i] in 70..74) countE+=2
            if(cards[i] in 75..78) countE+=3
            if(cards[i] in 79..81) countE+=4
            if(cards[i] == 87) greenI = true
            if(cards[i] == 89) countI+=5
            if(cards[i] == 90) countI+=3
        }
        var countTwo = countT2*countT2
        if(greenI) countTwo *= 2
        var pts = countT1*countT1 + countTwo + countT3*countT3 + countE*2 + countM + countP + countH + countI
        firebase.getReference(gameID.toString()).child(team).child("VictoryPoints").setValue(pts)
    }

    fun checkCard(pay : Int, what : String) : Boolean{
        var check = false
        when(getMyTeam()){
            "Red" -> {
                if(what == "Money")
                    check = rDeck[3] >= pay
                else if(what == "Energy")
                    check = rDeck[4] >= pay
            }
            "Blue" -> {
                if(what == "Money")
                    check = bDeck[3] >= pay
                else if(what == "Energy")
                    check = bDeck[4] >= pay
            }
            "Green" -> {
                if(what == "Money")
                    check = gDeck[3] >= pay
                else if(what == "Energy")
                    check = gDeck[4] >= pay
            }
            "Yellow" -> {
                if(what == "Money")
                    check = yDeck[3] >= pay
                else if(what == "Energy")
                    check = yDeck[4] >= pay
            }
        }
        //TODO if check false mostrare avviso irregolarità a schermo
        return check
    }

    fun   uploadDeck(c : Int, team : String){
        var deck = arrayOf(0,0,0,0,0,0,0)

        if(c == 86){
            when (team){
                "Red" -> deck[5] += rDeck[4]
                "Blue" -> deck[5] += bDeck[4]
                "Green" -> deck[5] += gDeck[4]
                "Yellow" -> deck[5] += yDeck[4]
                "My" -> deck[5] += myDeck[4]
            }
        }

        if(c <= 5 || c == 91) deck[0]++
        if(c in 6..10 || c == 92) deck[1]++
        if(c in 11..15 || c == 93) deck[2]++
        if(c in 16..20) deck[3]++
        if(c in 21..25) {
            deck[3]+=3
            deck[6]--
        }
        if(c in 26..30) {
            deck[3]+=7
            deck[6]-=2
        }
        if(c in 31..35) deck[4]++
        if(c in 36..40) {
            deck[4]+=2
            deck[5]--
        }
        if(c in 41..45) {
            deck[4]+=5
            deck[5]-=2
        }
        if(c in 46..51) deck[5]++
        if(c in 52..56) deck[5]+=2
        if(c in 57..60) deck[5]+=3
        if(c in 61..63) deck[5]+=4
        if(c in 64..69) deck[6]++
        if(c in 70..74) deck[6]+=2
        if(c in 75..78) deck[6]+=3
        if(c in 79..81) deck[6]+=4

        if(c == 82) deck[4]--
        if(c == 83) deck[3]--
        if(c == 84) deck[6]--
        if(c == 85) deck[5]--

       // println("deck $deck")

        when(team){
            "Red" -> {rDeck = rDeck.zip(deck) { xv, yv -> xv + yv } as ArrayList<Int> }
            "Blue" -> {bDeck = bDeck.zip(deck) { xv, yv -> xv + yv } as ArrayList<Int> }
            "Yellow" -> {yDeck = yDeck.zip(deck) { xv, yv -> xv + yv } as ArrayList<Int> }
            "Green" -> {gDeck = gDeck.zip(deck) { xv, yv -> xv + yv } as ArrayList<Int> }
            "My" -> {myDeck = myDeck.zip(deck) { xv, yv -> xv + yv } as ArrayList<Int> }
        }
    }

    fun getMyCards(): ArrayList<Int> {
        return myCards
    }

    fun goToCards2(first: Int, second: Int, i: Int) {
        mutableScreenName.value = ScreenName.Cards2(getMyTeam(), first, second, i)
        //TODO try catch da mettere a posto
    }

    fun goToCards3(first: Int, second: Int, i: Int, check : Int, x : Int) {
        mutableScreenName.value = ScreenName.Cards3(getMyTeam(), first, second, i, check, x)
        //TODO try catch da mettere a posto
    }

    fun setVehicleFromUnexpected(i : Int, check : Int){
        when (i){
            1 -> {
                if(check == 1) card1 = 91
                if(check == 2) card1 = 92
                if(check == 3) card1 = 93
            }
            2 -> {
                if(check == 1) card2 = 91
                if(check == 2) card2 = 92
                if(check == 3) card2 = 93
            }
            3 -> {
                if(check == 1) card3 = 91
                if(check == 2) card3 = 92
                if(check == 3) card3 = 93
            }
            4 -> {
                if(check == 1) card4 = 91
                if(check == 2) card4 = 92
                if(check == 3) card4 = 93
            }
            5 -> {
                if(check == 1) card5 = 91
                if(check == 2) card5 = 92
                if(check == 3) card5 = 93
            }
        }
    }

    fun setPenaltyFromUnexpected(i : Int, card : Int, team : Int){
        var teams = teamNames
        teams.remove(getMyTeam()) //TODO verificare che sia giusto, mi sa di no
        firebase.getReference(gameID.toString()).child(teams[team-1]).child("Cards")
            .child(card.toString()).setValue(card)
        when(i){
            1 -> { card1 = 0}
            2 -> { card2 = 0}
            3 -> { card3 = 0}
            4 -> { card4 = 0}
            5 -> { card5 = 0}
        }
    }
}