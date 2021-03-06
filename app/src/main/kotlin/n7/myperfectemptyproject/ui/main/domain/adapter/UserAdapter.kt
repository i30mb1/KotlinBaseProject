package n7.myperfectemptyproject.ui.main.domain.adapter

import java.text.SimpleDateFormat
import java.util.Locale
import n7.myperfectemptyproject.data.source.local.model.LocalUser
import n7.myperfectemptyproject.data.source.remote.model.RemoteUser
import n7.myperfectemptyproject.ui.main.domain.vo.VOUser

// The main task of adapters is to convert the entities used by the database
// and network clients to Domain module entities and back. This conversion has both pros and cons
// - Any data changes in one layer don’t affect other layers
// - Third-party dependencies as well as annotations required for a library don’t fall into other layers
// - There’s a possibility of multiple duplications
// - While changing the data you have to change the mapper

// one day i will use this somehow
// https://riggaroo.co.za/lessons-learnt-with-kotlin-using-inline-classes/
fun RemoteUser.toLocalStore(): LocalUser {
    return LocalUser(
        firstName = name.first,
        lastName = name.last,
        pictureUrl = picture.large,
        date = registered.date
    )
}

fun LocalUser.toVo(): VOUser {
    return VOUser().also {
        it.firstName = firstName
        it.lastName = lastName
        it.pictureUrl = pictureUrl
        it.date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date!!)
    }
}
