package david.ceballos.demo.scenes.home.model

import androidx.lifecycle.MutableLiveData

data class HomeModel (
    val name: MutableLiveData<String> = MutableLiveData<String>()
)