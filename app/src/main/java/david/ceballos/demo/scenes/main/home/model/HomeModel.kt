package david.ceballos.demo.scenes.main.home.model

import androidx.lifecycle.MutableLiveData

data class HomeModel (
    val name: MutableLiveData<String> = MutableLiveData<String>()
)