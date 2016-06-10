package xyz.myskng.ikastagewidget.tools

import android.app.Activity
import android.view.View
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
object Views {

    fun <V: View> bind(activity: Activity, id: Int): Lazy<V> = lazy { activity.findViewById(id) as V }
    fun <V: View> bind(fragment: android.support.v4.app.Fragment, id: Int): Bind4<V> = Bind4(fragment, id)
    fun <V: View> bind(fragment: android.app.Fragment, id: Int): Bind<V> = Bind(fragment, id)
    fun <V: View> bind(view: View, id: Int): Lazy<V> = lazy { view.findViewById(id) as V }

    class Bind<V: View>(val fragment: android.app.Fragment, val id: Int) : ReadOnlyProperty<Any, V> {

        override fun getValue(thisRef: Any, property: KProperty<*>): V {
            return fragment.view?.findViewById(id) as V
        }
    }

    class Bind4<V: View>(val fragment: android.support.v4.app.Fragment, val id: Int) : ReadOnlyProperty<Any, V> {

        override fun getValue(thisRef: Any, property: KProperty<*>): V {
            return fragment.view?.findViewById(id) as V
        }
    }
}