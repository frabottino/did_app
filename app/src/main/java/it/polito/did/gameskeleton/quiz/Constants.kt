package it.polito.did.gameskeleton.quiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import it.polito.did.gameskeleton.screens.QuizScreen

object Constants {

    const val USER_NAME : String = "user_name"
    const val TOTAL_QUESTIONS : String = "total_questions"
    const val CORRECT_ANS : String = "correct_answers"

    var quizCount by mutableStateOf(0)

    fun getQuestions():ArrayList<Question>{

        val questionsList = ArrayList<Question>()

        val que1 = Question(
            1,"Quando sono state costruite le prime centrali idroelettriche funzionanti al mondo?",
            "Nel XVIII secolo","Nel XIX secolo",
            "Nel XX secolo", 2

        )
        questionsList.add(que1)

        val que2 = Question(
            2," Qual è il processo che le centrali idroelettriche utilizzano per generare energia elettrica? ",
            "Trasformazione dell'energia solare in meccanica e poi in elettrica",
            "Trasformazione dell'energia cinetica dell'acqua in solare e poi in elettrica",
            "Trasformazione dell'energia cinetica dell'acqua in meccanica e poi in elettrica",
            3

        )
        questionsList.add(que2)

        val que3 = Question(
            3,"Quali sono i diversi tipi di centrali idroelettriche?",
            "Solo centrali ad acqua fluente",
            "Solo centrali a serbatoio",
            "Centrali ad acqua fluente, centrali a serbatoio, centrali ad accumulo, centrali a pompaggio, centrali mareomotrici",
            3

        )
        questionsList.add(que3)

        val que4 = Question(
            4,"Come funzionano le centrali ad acqua fluente?",
            "Utilizzano l'energia termica dell'acqua per far girare le turbine",
            "Utilizzano l'energia cinetica dell'acqua in movimento per far girare le turbine e generare energia elettrica ",
            "Utilizzano l'energia solare per far girare le turbine",
            2

        )
        questionsList.add(que4)

        val que5 = Question(
            5,"Qual è il vantaggio delle centrali ad accumulo rispetto ad altre centrali idroelettriche??",
            "Possono immagazzinare energia per restituirlo in momenti di picco di domanda","Non necessitano di un corso d'acqua costante",
            "Sono più economiche da costruire delle altre centrali idroelettriche", 1

        )
        questionsList.add(que5)


        val que6 = Question(
            6,"Come funzionano le centrali a serbatoio?",
            "Possono immagazzinare energia per restituirlo in momenti di picco di domanda",
            "Non necessitano di un corso d'acqua costante",
            "Sono più economiche da costruire delle altre centrali idroelettriche",
            1

        )
        questionsList.add(que6)

        val que7 = Question(
            7,"Qual è il metodo utilizzato dalle centrali mareomotrici per generare energia elettrica?",
            "Sfruttano l'energia del sole per far evaporare l'acqua del mare",
            "Utilizzano la pressione dell'acqua del mare per far girare le turbine",
            "Sfruttano l'energia cinetica dell'acqua in movimento causata dalle maree",
            3

        )
        questionsList.add(que7)

        val que8 = Question(
            8,"Qual è l'aspetto più vantaggioso dell'energia idroelettrica dal punto di vista ambientale?",
            "Non emette gas a effetto serra o altre sostanze inquinanti",
            "Non necessita di grandi quantità di acqua per funzionare",
            "Non richiede la costruzione di grandi dighe o serbatoi",
            1

        )
        questionsList.add(que8)

        val que9 = Question(
            9,"Quando sono state sviluppate le prime pale eoliche moderne?",
            "Nel Medioevo",
            "Nel XIX secolo",
            "Negli anni '70",
            3

        )
        questionsList.add(que9)

        val que10 = Question(
            10,"Quanti tipi di pale eoliche esistono?",
            "2",
            "3",
            "4",
            3

        )
        questionsList.add(que10)

        val que11 = Question(
            11,"Quali sono le caratteristiche delle pale eoliche orizzontali?",
            "Hanno le pale che ruotano attorno a un asse verticale",
            "Possono raggiungere altezze di decine di metri",
            "Sono meno efficienti delle pale eoliche verticali",
            2

        )
        questionsList.add(que11)

        val que12 = Question(
            12,"Dove sono spesso utilizzate le pale eoliche verticali?",
            "Utilizzano l'energia solare per generare energia elettrica",
            "Sfruttano la forza del vento per far girare le turbine",
            "Pompano l'acqua in un serbatoio posto a un'altezza superiore per immagazzinare energia ",
            2

        )
        questionsList.add(que12)

        val que13 = Question(
            13,"Qual è il vantaggio delle turbine a asse verticale rispetto alle pale eoliche orizzontali?",
            "Possono funzionare anche con venti più deboli",
            "Sono più efficienti",
            "Sono più economiche",
            1

        )
        questionsList.add(que13)

        val que14 = Question(
            14,"Dove sono installate le turbine offshore?",
            "In mare aperto",
            "In città",
            "In montagna",
            1

        )
        questionsList.add(que14)

        val que15 = Question(
            15,"Qual è l'effetto delle pale eoliche sul paesaggio? ",
            "Possono essere utilizzate come barriere antirumore",
            "Non hanno alcun impatto visivo sul paesaggio",
            "Possono avere un impatto visivo sul paesaggio, ma molte persone le considerano un elemento architettonico interessante e un'attrazione turistica",
            3

        )
        questionsList.add(que15)

        val que16 = Question(
            16,"Qual è la funzione dei pannelli solari?",
            "Trasformare la luce solare in calore",
            "Trasformare la luce solare in energia elettrica",
            "Trasformare l'energia cinetica in energia elettrica",
            2

        )
        questionsList.add(que16)

        val que17 = Question(
            17,"Quando sono stati sviluppati i primi pannelli solari?",
            "Negli anni '50",
            "Negli anni '70",
            "PNegli anni '90",
            1

        )
        questionsList.add(que17)

        val que18 = Question(
            18,"Di cosa sono costituiti principalmente i pannelli solari?",
            "Celle solari in vetro o plastica",
            "Celle fotovoltaiche in alluminio",
            "Celle fotovoltaiche in silicio",
            3

        )
        questionsList.add(que18)

        val que19 = Question(
            19,"Dove possono essere installati i pannelli solari?",
            "Solo su tettoie",
            "Solo su tetti",
            "Sulle tettoie, i tetti, le facciate e i terreni",
            3

        )
        questionsList.add(que19)

        val que20 = Question(
            20,"Cosa significa che i pannelli solari possono essere utilizzati in modo indipendente dalle reti di distribuzione elettrica?",
            "Che possono produrre energia solo in presenza di una connessione alla rete elettrica",
            "Che possono produrre energia in modo autonomo e autosufficiente",
            "Che non possono essere utilizzati per alimentare l'intero edificio",
            2

        )
        questionsList.add(que20)

        val que21 = Question(
            21,"Di cosa dipende l'efficienza dei pannelli solari?",
            "Dalle fasi della luna",
            "Dalle condizioni meteorologiche, dalla posizione geografica e dall'orientamento del pannello",
            "Dal tipo di materiale utilizzato per costruire il pannello",
            2

        )
        questionsList.add(que21)

        val que22 = Question(
            22,"Perché l'energia solare sta diventando sempre più economica?",
            "Grazie alla tecnologia in continua evoluzione e all'aumento della produzione su larga scala",
            "A causa della diminuzione del costo dei combustibili fossili",
            "A causa del minor utilizzo dei pannelli solari",
            1

        )
        questionsList.add(que22)

        val que23 = Question(
            23,"Come possono i pannelli solari contribuire alla lotta contro il cambiamento climatico?",
            "Riducendo la dipendenza dai combustibili fossili",
            "Aumentando le emissioni di gas a effetto serra",
            "Non avendo alcun effetto sul cambiamento climatico",
            1

        )
        questionsList.add(que23)

        val que24 = Question(
            24,"Quali sono i materiali comunemente utilizzati per le celle fotovoltaiche?",
            "Rame e alluminio",
            "Platino e oro",
            "Silicio e altri materiali semiconduttori",
            3

        )
        questionsList.add(que24)

        val que25 = Question(
            25,"Che cosa sono le zone verdi?",
            "Zone industriali designate per la conservazione della natura",
            "Aree urbane esenti da spazi verdi",
            "Aree designate per la conservazione della natura e della biodiversità",
            3

        )
        questionsList.add(que25)

        val que26 = Question(
            26,"Come possono essere mantenute le zone verdi?",
            "Lasciando crescere le piante come vogliono",
            "Utilizzando tecniche di gestione del paesaggio",
            "Utilizzando pesticidi sintetici",
            2

        )
        questionsList.add(que26)

        val que27 = Question(
            27,"Chi costruì il primo carro elettrico nel 1830?",
            "Thomas Parker",
            "Robert Anderson",
            "Nikola Tesla",
            2

        )
        questionsList.add(que27)

        val que28 = Question(
            28,"Cosa rende le auto elettriche più ecologiche delle auto tradizionali?",
            "Non hanno bisogno di cambi di olio e non emettono gas di scarico",
            "Non hanno bisogno di essere lavate",
            "Non hanno bisogno di essere parcheggiate",
            1

        )
        questionsList.add(que28)

        val que29 = Question(
            29,"Cosa differenzia le auto elettriche dalle auto a motore a combustione interna?",
            "Sono meno costose",
            "Hanno una maggiore velocità massima",
            "Hanno una maggiore coppia e accelerazione ma una velocità massima inferiore",
            3

        )
        questionsList.add(que29)

        val que30 = Question(
            30,"Che tipo di batterie utilizzano molte auto elettriche?",
            "Batterie al piombo",
            "Batterie al nichel-metallo",
            "Batterie agli ioni di litio",
            3

        )
        questionsList.add(que30)

        val que31 = Question(
            31,"Cosa influenza l'autonomia delle auto elettriche?",
            "La capacità della batteria e la guida effettuata",
            "Il colore della carrozzeria",
            "La velocità massima dell'auto",
            1

        )
        questionsList.add(que31)

        val que32 = Question(
            32,"In quali paesi i proprietari di auto elettriche possono beneficiare di incentivi governativi?",
            "Solo in paesi dell'Unione Europea",
            "Solo in paesi dell'America del Nord",
            "In molti paesi, tra cui l'Italia",
            3

        )
        questionsList.add(que32)

        val que33 = Question(
            33,"Quali sono i modi per ricaricare un'auto elettrica?",
            "Solo la ricarica veloce",
            "La ricarica domestica, la ricarica pubblica e la ricarica veloce",
            "Solo la ricarica solare",
            2

        )
        questionsList.add(que33)

        val que34 = Question(
            34,"Cosa consentono di fare i pannelli solari sul tetto delle auto elettriche?",
            "Consentono di ricaricare la batteria durante la guida",
            "Consentono di creare ombra all'interno dell'auto",
            "Consentono di far risparmiare energia durante la guida",
            1

        )
        questionsList.add(que34)

        val que35 = Question(
            35,"Cosa stanno facendo molte grandi città per facilitare la guida delle auto elettriche?",
            "Stanno vietando la circolazione delle auto elettriche",
            "Stanno creando infrastrutture di ricarica pubblica",
            "Stanno aumentando le tasse sulle auto elettriche",
            2

        )
        questionsList.add(que35)

        val que36 = Question(
            36,"In cosa consistono le case ecologiche?",
            "Sono case progettate per minimizzare l'impatto ambientale",
            "Sono case con una grande impronta ecologica",
            "Sono case che non tengono in considerazione l'impatto ambientale",
            1

        )
        questionsList.add(que36)

        val que37 = Question(
            37,"Con quali materiali possono essere costruite le case ecologiche?",
            "Solo con materiali costosi e difficili da reperire",
            "Con materiali sostenibili o riciclati",
            "Solo con materiali di nuova generazione",
            2

        )
        questionsList.add(que37)

        val que38 = Question(
            38,"Come vengono progettate le case ecologiche per massimizzare l'efficienza energetica?",
            "Utilizzando solo energia solare",
            "Aumentando il consumo di energia",
            "Attraverso l'isolamento termico e il controllo della radiazione solare",
            3

        )
        questionsList.add(que38)

        val que39 = Question(
            39,"Quali fonti di energia rinnovabile possono essere utilizzate dalle case ecologiche?",
            "Solo energia solare",
            "Solo energia solare ed eolica",
            "Tutti i tipi di energie rinnovabili",
            3

        )
        questionsList.add(que39)

        val que40 = Question(
            40,"A cosa possono servire i sistemi di raccolta dell'acqua piovana nelle case ecologiche?",
            "Per l'irrigazione e il consumo non potabile",
            "Solo per l'irrigazione",
            "Solo per il consumo potabile",
            1

        )
        questionsList.add(que40)

        val que41 = Question(
            41,"Cosa sono le caratteristiche di design bioclimatico nelle case ecologiche?",
            "Non influenzano l'efficienza energetica della casa",
            "Servono a massimizzare l'efficienza energetica e il comfort degli occupanti",
            "Sono un optional e non vengono considerate durante la progettazione della casa",
            2

        )
        questionsList.add(que41)

        val que42 = Question(
            42,"Come possono essere progettate le case ecologiche per ridurre l'impatto ambientale durante la costruzione?",
            "Utilizzando solo materiali di nuova generazione",
            "Utilizzando solo materiali non riciclabili",
            "Utilizzando materiali riciclabili e riducendo i consumi",
            3

        )
        questionsList.add(que42)

        val que43 = Question(
            43,"Da quali organizzazioni possono essere certificate le case ecologiche?",
            "Organizzazione Mondiale della Sanità",
            "Leadership in Energy and Environmental Design",
            "Unione Europea",
            2

        )
        questionsList.add(que43)

        val que44 = Question(
            44,"Qual è la fonte di energia rinnovabile più utilizzata al mondo?",
            "Solare",
            "Idroelettrica",
            "Eolica",
            2

        )
        questionsList.add(que44)

        val que45 = Question(
            45,"Qual è la fonte di energia rinnovabile più utilizzata in Italia?",
            "Solare",
            "Idroelettrica",
            "Eolica",
            3

        )
        questionsList.add(que45)

        val que46 = Question(
            46,"Qual è la fonte di energia rinnovabile che ha il maggior potenziale di sviluppo in Italia?",
            "Solare",
            "Idroelettrica",
            "Eolica",
            1

        )
        questionsList.add(que46)

        val que47 = Question(
            47,"Qual è il principale ostacolo allo sviluppo delle energie rinnovabili in Italia?",
            "Costi elevati",
            "Difficoltà di integrazione nella rete elettrica",
            "Scarsa disponibilità di risorse naturali",
            2

        )
        questionsList.add(que47)

        val que48 = Question(
            48,"Qual è il principale vantaggio delle energie rinnovabili rispetto alle fonti fossili?",
            "Minori emissioni di gas serra",
            "Maggiore disponibilità di risorse",
            "Maggiore efficienza energetica",
            1

        )
        questionsList.add(que48)

        val que49 = Question(
            49,"Qual è il principale svantaggio delle energie rinnovabili rispetto alle fonti fossili?",
            "Costi più elevati",
            "Scarsa affidabilità",
            "Minore efficienza energetica",
            1

        )
        questionsList.add(que49)

        val que50 = Question(
            50,"Qual è il principale vantaggio dell'energia solare?",
            "Facilità di integrazione nella rete elettrica",
            "Bassi costi di produzione",
            "Disponibilità diffusa",
            3

        )
        questionsList.add(que50)

        val que51 = Question(
            51,"Qual è il principale svantaggio dell'energia solare?",
            "Costi elevati",
            "Dipendenza dalle condizioni meteorologiche",
            "carsa efficienza energetica",
            2

        )
        questionsList.add(que51)

        val que52 = Question(
            52,"Qual è il principale vantaggio dell'energia eolica?",
            "Elevata efficienza energetica",
            "Bassi costi di produzione",
            "Facilità di integrazione nella rete elettrica",
            1

        )
        questionsList.add(que52)

        val que53 = Question(
            53,"Qual è il principale svantaggio dell'energia eolica?",
            "Costi elevati",
            "Impatto ambientale negativo",
            "Dipendenza dalle condizioni meteorologiche",
            3

        )
        questionsList.add(que53)

        val que54 = Question(
            54,"Qual è il principale vantaggio dell'energia idroelettrica?",
            "Bassi costi di produzione",
            "Elevata efficienza energetica",
            "Disponibilità diffusa",
            2

        )
        questionsList.add(que54)

        val que55 = Question(
            55,"Qual è il principale svantaggio dell'energia idroelettrica?",
            "Costi elevati",
            "scarsa affidabilità",
            "Impatto ambientale negativo",
            3

        )
        questionsList.add(que55)
        return questionsList
    }

    fun setQuestionText(id : Int) : Question{
        return getQuestions()[id]
    }

    fun checkAnswer(ansId : Int, questId : Question) : Boolean{
        if(ansId == questId.correctAnswer) {
            quizCount++
        }
        else {
            quizCount--
        }
        return ansId == questId.correctAnswer
    }
}