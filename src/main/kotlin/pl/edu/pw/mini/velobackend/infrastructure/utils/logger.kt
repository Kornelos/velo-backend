package pl.edu.pw.mini.velobackend.infrastructure.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import kotlin.reflect.KProperty

class LoggerDelegate {

    private var logger: Logger? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Logger {
        if (logger == null) logger = getLogger(thisRef!!.javaClass.name)
        return logger!!
    }

}

fun logger() = LoggerDelegate()
