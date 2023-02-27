package com.example.demo_maciek.gui

import com.example.demo_maciek.model.Guest
import com.github.mvysny.karibudsl.v10.*
import com.github.mvysny.karibudsl.v23.footer
import com.github.mvysny.kaributools.refresh
import com.github.vokorm.dataloader.dataLoader
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.router.Route
import eu.vaadinonkotlin.vaadin.vokdb.asDataProvider

@Route("person-view")
class GuestView : KComposite() {
    val root = ui {
        lateinit var form: PersonForm
        lateinit var guest: Guest
        lateinit var grid: Grid<Guest>

        verticalLayout {
            setSizeFull()
            h1("GUESTS LIST")
            var totalGuests = countGuests()
            label("Guests count: $totalGuests")
            val dlg = dialog {
                form = personForm {}
                footer {
                    button("Save") {
                        onLeftClick {
                            if (form.binder.validate().isOk && form.binder.writeBeanIfValid(guest)) {
                                guest.save()
                                grid.refresh()
                                this@dialog.close()
                            }
                        }
                    }
                }
            }
            grid = grid {
                dataProvider = Guest.dataLoader.asDataProvider()
                columnFor(Guest::name)
                columnFor(Guest::age)
                columnFor(Guest::status)
                addComponentColumn { currentRow ->
                    Button(VaadinIcon.EDIT.create()) {
                        guest = currentRow
                        form.binder.readBean(guest)
                        form.binder.validate()
                        dlg.open()
                    }
                }
            }
            horizontalLayout {
                button("Add") {
                    addThemeVariants(ButtonVariant.LUMO_PRIMARY)
                    onLeftClick {
                        guest = Guest()
                        form.binder.readBean(guest)
                        form.binder.validate()
                        dlg.open()
                    }
                }
            }
        }
    }

    private fun countGuests(): Int {
        val guests = Guest.findAll()
        var totalGuests = 0
        for (guest in guests) {
            var tempGuests = 0
            if (guest.name?.contains("+") == true
                || guest.name?.contains(" i ") == true
                || guest.name?.contains(" oraz ") == true
            ) {
                tempGuests += 2
            } else {
                tempGuests++
            }
            totalGuests += tempGuests
        }
        return totalGuests
    }
}

