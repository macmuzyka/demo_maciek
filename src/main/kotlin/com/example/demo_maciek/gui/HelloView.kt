package com.example.demo_maciek.gui

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.h1
import com.github.mvysny.karibudsl.v10.numberField
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.github.mvysny.karibudsl.v10.routerLink
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.confirmdialog.ConfirmDialog
import com.vaadin.flow.router.Route


@Route("")
class HelloView : KComposite() {
    private lateinit var button: Button
    val root = ui {
        verticalLayout {
            h1("Hello world")
            val liczba = numberField("Liczba") {
                value = 1.0
                isClearButtonVisible = true
                addValueChangeListener {
                    button.isEnabled = (it.value ?: 0.0) <= 2.0
                }
            }
            val dlg = ConfirmDialog("Pytanko", "Czy zwiekszyć liczbę?", "Tak") {
                liczba.value = (liczba.value ?: 0.0).plus(1)
            }
            button = button("Click me") {
                onLeftClick {
                    dlg.open()
                }
            }
            routerLink(text = "Synonimy", viewType = SynonimyView::class)
            routerLink(text = "Person", viewType = PersonView::class)
        }
    }
}