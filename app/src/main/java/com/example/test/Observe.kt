package com.example.test

import android.app.Application
import android.os.Handler
import android.os.Looper

class Observe : Application() {

    var handler: Handler = Handler(Looper.getMainLooper())

    var listeners = HashSet<ObserveInterface>()
    //методи, що викликаємо з фрагментів
    public fun addListener(obs:ObserveInterface){
        listeners.add(obs)
    }

    public fun removeListener(obs:ObserveInterface){
        listeners.remove(obs)
    }

    //метод, якщо з сервісу передаємо дані:
    public fun  ReadFromFile(str:String){
        //activity повинна працювати в головному потоці, саме через це ми иконуємо handler (якось так)
        handler.post {for(listener:ObserveInterface in listeners){
            listener.onReadFromFile(str)} }
    }
}