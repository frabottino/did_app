package it.polito.did.gameskeleton

import com.example.did_app.R

object ConstantCards {

    private val cardsList = ArrayList<Card>()
    private val allCards = ArrayList<Card>()
    private val instantCardsList = ArrayList<Card>()
    private val permanentCardsList = ArrayList<Card>()

    private val transCard1 = Card(
        0,99, 1, R.drawable.card01
    )
    private val transCard2 = Card(
        0,2, 1, R.drawable.card02
    )
    private val transCard3 = Card(
        0,3, 1, R.drawable.card03
    )
    private val transCard4 = Card(
        0,4, 1, R.drawable.card04
    )
    private val transCard5 = Card(
        0,5, 1, R.drawable.card05
    )
    private val transCard6 = Card(
        0,6, 2, R.drawable.card06
    )
    private val transCard7 = Card(
        0,7, 2, R.drawable.card07
    )
    private val transCard8 = Card(
        0,8, 2, R.drawable.card08
    )
    private val transCard9 = Card(
        0,9, 2, R.drawable.card09
    )
    private val transCard10 = Card(
        0,10, 2, R.drawable.card10
    )
    private val transCard11 = Card(
        0,11, 3, R.drawable.card11
    )
    private val transCard12 = Card(
        0,12, 3, R.drawable.card12
    )
    private val transCard13 = Card(
        0,13, 3, R.drawable.card13
    )
    private val transCard14 = Card(
        0,14, 3, R.drawable.card14
    )
    private val transCard15 = Card(
        0,15, 3, R.drawable.card15
    )
    private val powerCard1 = Card(
        1, 16, 1, R.drawable.card18
    )
    private val powerCard2 = Card(
        1, 17, 1, R.drawable.card18
    )
    private val powerCard3 = Card(
        1, 18, 1, R.drawable.card18
    )
    private val powerCard4 = Card(
        1, 19, 1, R.drawable.card18
    )
    private val powerCard5 = Card(
        1, 20, 1, R.drawable.card18
    )
    private val powerCard6 = Card(
        1, 21, 2, R.drawable.card19
    )
    private val powerCard7 = Card(
        1, 22, 2, R.drawable.card19
    )
    private val powerCard8 = Card(
        1, 23, 2, R.drawable.card19
    )
    private val powerCard9 = Card(
        1, 24, 2, R.drawable.card19
    )
    private val powerCard10 = Card(
        1, 25, 2, R.drawable.card19
    )
    private val powerCard11 = Card(
        1, 26, 3, R.drawable.card20
    )
    private val powerCard12 = Card(
        1, 27, 3, R.drawable.card20
    )
    private val powerCard13 = Card(
        1, 28, 3, R.drawable.card20
    )
    private val powerCard14 = Card(
        1, 29, 3, R.drawable.card20
    )
    private val powerCard15 = Card(
        1, 30, 3, R.drawable.card20
    )
    private val houseCard1 = Card(
        2, 31, 1, R.drawable.card21
    )
    private val houseCard2 = Card(
        2, 32, 1, R.drawable.card21
    )
    private val houseCard3 = Card(
        2, 33, 1, R.drawable.card21
    )
    private val houseCard4 = Card(
        2, 34, 1, R.drawable.card21
    )
    private val houseCard5 = Card(
        2, 35, 1, R.drawable.card21
    )
    private val houseCard6 = Card(
        2, 36, 2, R.drawable.card22
    )
    private val houseCard7 = Card(
        2, 37, 2, R.drawable.card22
    )
    private val houseCard8 = Card(
        2, 38, 2, R.drawable.card22
    )
    private val houseCard9 = Card(
        2, 39, 2, R.drawable.card22
    )
    private val houseCard10 = Card(
        2, 40, 2, R.drawable.card22
    )
    private val houseCard11 = Card(
        2, 41, 3, R.drawable.card23
    )
    private val houseCard12 = Card(
        2, 42, 3, R.drawable.card23
    )
    private val houseCard13 = Card(
        2, 43, 3, R.drawable.card23
    )
    private val houseCard14 = Card(
        2, 44, 3, R.drawable.card23
    )
    private val houseCard15 = Card(
        2, 45, 3, R.drawable.card23
    )
    private val moneyCard1 = Card(
        3, 46, 1, R.drawable.money_1
    )
    private val moneyCard2 = Card(
        3, 47, 1, R.drawable.money_1
    )
    private val moneyCard3 = Card(
        3, 48, 1, R.drawable.money_1
    )
    private val moneyCard4 = Card(
        3, 49, 1, R.drawable.money_1
    )
    private val moneyCard5 = Card(
        3, 50, 1, R.drawable.money_1
    )
    private val moneyCard6 = Card(
        3, 51, 1, R.drawable.money_1
    )
    private val moneyCard7 = Card(
        3, 52, 2, R.drawable.money_2
    )
    private val moneyCard8 = Card(
        3, 53, 2, R.drawable.money_2
    )
    private val moneyCard9 = Card(
        3, 54, 2, R.drawable.money_2
    )
    private val moneyCard10 = Card(
        3, 55, 2, R.drawable.money_2
    )
    private val moneyCard11 = Card(
        3, 56, 2, R.drawable.money_3
    )
    private val moneyCard12 = Card(
        3, 57, 3, R.drawable.money_3
    )
    private val moneyCard13 = Card(
        3, 58, 3, R.drawable.money_3
    )
    private val moneyCard14 = Card(
        3, 59, 3, R.drawable.money_3
    )
    private val moneyCard15 = Card(
        3, 60, 3, R.drawable.money_3
    )
    private val moneyCard16 = Card(
        3, 61, 4, R.drawable.money_4
    )
    private val moneyCard17 = Card(
        3, 62, 4, R.drawable.money_4
    )
    private val moneyCard18 = Card(
        3, 63, 4, R.drawable.money_4
    )
    private val energyCard1 = Card(
        4, 64, 1, R.drawable.energy_1
    )
    private val energyCard2 = Card(
        4, 65, 1, R.drawable.energy_1
    )
    private val energyCard3 = Card(
        4, 66, 1, R.drawable.energy_1
    )
    private val energyCard4 = Card(
        4, 67, 1, R.drawable.energy_1
    )
    private val energyCard5 = Card(
        4, 68, 1, R.drawable.energy_1
    )
    private val energyCard6 = Card(
        4, 69, 1, R.drawable.energy_1
    )
    private val energyCard7 = Card(
        4, 70, 2, R.drawable.energy_2
    )
    private val energyCard8 = Card(
        4, 71, 2, R.drawable.energy_2
    )
    private val energyCard9 = Card(
        4, 72, 2, R.drawable.energy_2
    )
    private val energyCard10 = Card(
        4, 73, 2, R.drawable.energy_2
    )
    private val energyCard11 = Card(
        4, 74, 2, R.drawable.energy_2
    )
    private val energyCard12 = Card(
        4, 75, 3, R.drawable.energy_3
    )
    private val energyCard13 = Card(
        4, 76, 3, R.drawable.energy_3
    )
    private val energyCard14 = Card(
        4, 77, 3, R.drawable.energy_3
    )
    private val energyCard15 = Card(
        4, 78, 3, R.drawable.energy_3
    )
    private val energyCard16 = Card(
        4, 79, 4, R.drawable.energy_4
    )
    private val energyCard17 = Card(
        4, 80, 4, R.drawable.energy_4
    )
    private val energyCard18 = Card(
        4, 81, 4, R.drawable.energy_4
    )
    //toglie una casa ad un team avversario
    private val instActCard1 = Card(
        5, 82, 1, R.drawable.card26
    )
    //toglie una centrale ad un team avversario
    private val instActCard2 = Card(
        5, 83, 2, R.drawable.card24
    )
    //toglie una energia ad un team avversario
    private val instActCard3 = Card(
        5, 84, 3, R.drawable.card25
    )
    //toglie una moneta ad un team avversario
    private val instActCard4 = Card(
        5, 85, 4, R.drawable.card28
    )
    //ottieni una moneta per ogni risorsa abitazione
    private val instActCard5 = Card(
        5, 86, 5, R.drawable.card27
    )
    //raddoppia i punti trasporto
    private val permActCard1 = Card(
        6, 87, 1, R.drawable.card29
    )
    //traforma la carta in un trasporto a scelta
    private val permActCard2 = Card(
        6, 88, 2, R.drawable.card30
    )
    private val permActCard3 = Card(
        6, 89, 3, R.drawable.card32
    )
    private val permActCard4 = Card(
        6, 90, 4, R.drawable.card31
    )
    private val bonusTrans1 = Card(
        6, 91, 4, R.drawable.bonuscard1
    )
    private val bonusTrans2 = Card(
        6, 92, 4, R.drawable.bonuscard2
    )
    private val bonusTrans3 = Card(
        6, 93, 4, R.drawable.bonuscard3
    )
    private val bonus1 = Card(
        6, 94, 4, R.drawable.bonustrans1
    )
    private val bonus2 = Card(
        6, 95, 4, R.drawable.bonustrans2
    )
    private val bonus3 = Card(
        6, 96, 4, R.drawable.bonustrans3
    )


    fun shuffleInstantCards() {
        instantCardsList.add(instActCard1)
        instantCardsList.add(instActCard2)
        instantCardsList.add(instActCard3)
        instantCardsList.add(instActCard4)
        instantCardsList.add(instActCard5)

        instantCardsList.shuffle()
    }

    fun shufflePermanentCards() {
        permanentCardsList.add(permActCard1)
        permanentCardsList.add(permActCard2)
        permanentCardsList.add(permActCard3)
        permanentCardsList.add(permActCard4)

        permanentCardsList.shuffle()
    }


    fun getCardList1() : ArrayList<Card> {

        cardsList.add(transCard1)
        cardsList.add(transCard6)
        cardsList.add(transCard11)
        cardsList.add(powerCard1)
        cardsList.add(powerCard2)
        cardsList.add(powerCard3)
        cardsList.add(houseCard1)
        cardsList.add(houseCard2)
        cardsList.add(houseCard3)
        cardsList.add(houseCard4)
        cardsList.add(moneyCard1)
        cardsList.add(moneyCard2)
        cardsList.add(moneyCard3)
        cardsList.add(moneyCard4)
        cardsList.add(moneyCard5)
        cardsList.add(moneyCard7)
        cardsList.add(moneyCard8)
        cardsList.add(moneyCard9)
        cardsList.add(moneyCard12)
        cardsList.add(energyCard1)
        cardsList.add(energyCard2)
        cardsList.add(energyCard3)
        cardsList.add(energyCard4)
        cardsList.add(energyCard5)
        cardsList.add(energyCard7)
        cardsList.add(energyCard8)
        cardsList.add(energyCard9)
        cardsList.add(energyCard12)

        cardsList.add(instantCardsList[0])
        cardsList.add(permanentCardsList[0])

        cardsList.shuffle()
        return cardsList
    }

    fun getCardList2() : ArrayList<Card> {
        cardsList.clear()

        cardsList.add(transCard2)
        cardsList.add(transCard7)
        cardsList.add(transCard8)
        cardsList.add(transCard12)
        cardsList.add(transCard13)
        cardsList.add(powerCard4)
        cardsList.add(powerCard5)
        cardsList.add(powerCard6)
        cardsList.add(powerCard7)
        cardsList.add(powerCard8)
        cardsList.add(houseCard5)
        cardsList.add(houseCard6)
        cardsList.add(houseCard7)
        cardsList.add(houseCard8)
        cardsList.add(moneyCard6)
        cardsList.add(moneyCard10)
        cardsList.add(moneyCard11)
        cardsList.add(moneyCard13)
        cardsList.add(moneyCard16)
        cardsList.add(moneyCard17)
        cardsList.add(energyCard6)
        cardsList.add(energyCard10)
        cardsList.add(energyCard11)
        cardsList.add(energyCard13)
        cardsList.add(energyCard16)
        cardsList.add(energyCard17)

        cardsList.add(instantCardsList[1])
        cardsList.add(instantCardsList[2])
        cardsList.add(permanentCardsList[1])
        cardsList.add(permanentCardsList[2])

        cardsList.shuffle()

        return cardsList
    }

    fun getCardList3() : ArrayList<Card> {
        cardsList.clear()

        cardsList.add(transCard3)
        cardsList.add(transCard4)
        cardsList.add(transCard5)
        cardsList.add(transCard9)
        cardsList.add(transCard10)
        cardsList.add(transCard14)
        cardsList.add(transCard15)
        cardsList.add(powerCard9)
        cardsList.add(powerCard10)
        cardsList.add(powerCard11)
        cardsList.add(powerCard12)
        cardsList.add(powerCard13)
        cardsList.add(powerCard14)
        cardsList.add(powerCard15)
        cardsList.add(houseCard9)
        cardsList.add(houseCard10)
        cardsList.add(houseCard11)
        cardsList.add(houseCard12)
        cardsList.add(houseCard13)
        cardsList.add(houseCard14)
        cardsList.add(houseCard15)
        cardsList.add(moneyCard14)
        cardsList.add(moneyCard15)
        cardsList.add(moneyCard18)
        cardsList.add(energyCard14)
        cardsList.add(energyCard15)
        cardsList.add(energyCard18)

        cardsList.add(instantCardsList[3])
        cardsList.add(instantCardsList[4])
        cardsList.add(permanentCardsList[3])

        cardsList.shuffle()

        return cardsList
    }

    fun displayCards() : ArrayList<Card>{
        var displayedCards = ArrayList<Card>()
        for(i in 0..4) {
            println("displaycard $i e basta")
            println("displaycard $i ${cardsList[i]}")
            displayedCards[i] = cardsList[i]
        }
        return displayedCards
    }

    fun pickCard(a : Int?, s : String) {
        println("before ${cardsList[0].idC}, ${cardsList[1].idC}, ${cardsList[2].idC}, ${cardsList[3].idC}, ${cardsList[4].idC} $s")
        if(a != null && a != -1) cardsList.removeAt(a-1)
        println("after ${cardsList[0].idC}, ${cardsList[1].idC}, ${cardsList[2].idC}, ${cardsList[3].idC}, ${cardsList[4].idC} $s")
    }

    fun getCurrentList() : ArrayList<Card>{
        return cardsList
    }

    fun getCardByID(id : Int) : Card{
        if(id > 100) return moneyCard1
        for (i in 0 until cardsList.size) {
            if (id == cardsList[i].idC)
                return cardsList[i]
        }
        return powerCard1
    }

    fun getAllCards() {
        cardsList.add(transCard1)
        cardsList.add(transCard6)
        cardsList.add(transCard11)
        cardsList.add(powerCard1)
        cardsList.add(powerCard2)
        cardsList.add(powerCard3)
        cardsList.add(houseCard1)
        cardsList.add(houseCard2)
        cardsList.add(houseCard3)
        cardsList.add(houseCard4)
        cardsList.add(moneyCard1)
        cardsList.add(moneyCard2)
        cardsList.add(moneyCard3)
        cardsList.add(moneyCard4)
        cardsList.add(moneyCard5)
        cardsList.add(moneyCard7)
        cardsList.add(moneyCard8)
        cardsList.add(moneyCard9)
        cardsList.add(moneyCard12)
        cardsList.add(energyCard1)
        cardsList.add(energyCard2)
        cardsList.add(energyCard3)
        cardsList.add(energyCard4)
        cardsList.add(energyCard5)
        cardsList.add(transCard2)
        cardsList.add(transCard7)
        cardsList.add(transCard8)
        cardsList.add(transCard12)
        cardsList.add(transCard13)
        cardsList.add(powerCard4)
        cardsList.add(powerCard5)
        cardsList.add(powerCard6)
        cardsList.add(powerCard7)
        cardsList.add(powerCard8)
        cardsList.add(houseCard5)
        cardsList.add(houseCard6)
        cardsList.add(houseCard7)
        cardsList.add(houseCard8)
        cardsList.add(moneyCard6)
        cardsList.add(moneyCard10)
        cardsList.add(moneyCard11)
        cardsList.add(moneyCard13)
        cardsList.add(moneyCard16)
        cardsList.add(moneyCard17)
        cardsList.add(energyCard6)
        cardsList.add(energyCard10)
        cardsList.add(energyCard11)
        cardsList.add(energyCard13)
        cardsList.add(energyCard16)
        cardsList.add(energyCard17)
        cardsList.add(energyCard7)
        cardsList.add(energyCard8)
        cardsList.add(energyCard9)
        cardsList.add(energyCard12)
        cardsList.add(transCard3)
        cardsList.add(transCard4)
        cardsList.add(transCard5)
        cardsList.add(transCard9)
        cardsList.add(transCard10)
        cardsList.add(transCard14)
        cardsList.add(transCard15)
        cardsList.add(powerCard9)
        cardsList.add(powerCard10)
        cardsList.add(powerCard11)
        cardsList.add(powerCard12)
        cardsList.add(powerCard13)
        cardsList.add(powerCard14)
        cardsList.add(powerCard15)
        cardsList.add(houseCard9)
        cardsList.add(houseCard10)
        cardsList.add(houseCard11)
        cardsList.add(houseCard12)
        cardsList.add(houseCard13)
        cardsList.add(houseCard14)
        cardsList.add(houseCard15)
        cardsList.add(moneyCard14)
        cardsList.add(moneyCard15)
        cardsList.add(moneyCard18)
        cardsList.add(energyCard14)
        cardsList.add(energyCard15)
        cardsList.add(energyCard18)
        cardsList.add(instActCard1)
        cardsList.add(instActCard2)
        cardsList.add(instActCard3)
        cardsList.add(instActCard4)
        cardsList.add(instActCard5)
        cardsList.add(permActCard1)
        cardsList.add(permActCard2)
        cardsList.add(permActCard3)
        cardsList.add(permActCard4)
        cardsList.add(bonusTrans1)
        cardsList.add(bonusTrans2)
        cardsList.add(bonusTrans3)
        cardsList.add(bonus1)
        cardsList.add(bonus2)
        cardsList.add(bonus3)
    }
}