package com.example.demo_maciek.gui

import com.example.demo_maciek.model.Person
import com.example.demo_maciek.model.Status
import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.data.binder.Binder

class PersonForm : UI() {
    val binder = Binder(Person::class.java)
    init {
        textField("Name:") {
            focus()
            bind(binder).trimmingConverter().bind(Person::name)
        }
        integerField("Age:") {
            bind(binder).bind(Person::age)
        }
        comboBox<Status>("Status:") {
            setItems(*Status.values())
            bind(binder).bind(Person::status)
        }
//        checkBox("Alive:")
    }

















    /*var

    val save = Button("Save")
    val delete = Button("Delete")
    val close = Button("Close")

    override fun add(components: MutableCollection<Component>?) {
        super.add(components)
    }


    private fun createButtonLayout(): HorizontalLayout {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY)
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR)
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY)

        save.addClickShortcut(Key.ENTER)
        close.addClickShortcut(Key.ESCAPE)
        return HorizontalLayout(save, delete, close)
    }*/
}