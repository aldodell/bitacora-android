package philosophicas.org.bitacora

/***** NO USAR NO TTERMINADA
 * */


 class KJson <T : Any> (){

    fun toJSON(obj: T) : String {

        var names =  ArrayList<String>()
        var types = ArrayList<String>()
        obj::class.members.forEach { k ->
            names.add(k.name)
        }


        var r = ""

        names.forEach { n ->
            r = "r $n"
        }
        return r
    }
}