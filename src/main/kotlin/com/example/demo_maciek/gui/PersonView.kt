package com.example.demo_maciek.gui

import com.example.demo_maciek.model.Person
import com.example.demo_maciek.model.Status
import com.github.mvysny.karibudsl.v10.*
import com.github.mvysny.kaributools.refresh
import com.github.vokorm.dataloader.dataLoader
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.notification.NotificationVariant
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.Route
import eu.vaadinonkotlin.vaadin.vokdb.asDataProvider

@Route("person-view")
class PersonView : KComposite() {
    private lateinit var nameField: TextField
    private lateinit var ageField: IntegerField
    private lateinit var statusField: ComboBox<Status>
    private lateinit var dialogButton: Button
    private lateinit var accept: Button
    private lateinit var cancel: Button
    private lateinit var dialog: Dialog

    val root = ui {
        verticalLayout {
            h1("LISTA GOŚCI")

            val guests = getGuests()

            label("Liczba gości: $guests") {}

            val grid = grid<Person> {
                isExpand = true
                columnFor(Person::name)
                columnFor(Person::age)
                columnFor(Person::status)
                dataProvider = Person.dataLoader.asDataProvider()
            }

            dialog = dialog {
                horizontalLayout {

                    nameField = textField("Imię:")
                    ageField = integerField("Wiek:")
                    statusField = comboBox("Status:") {
                        setItems(*Status.values())
                    }
                    accept = button("Dodaj") {
                        onLeftClick {
                            val notification: Notification
                            if (nameField.isEmpty || ageField.isEmpty || statusField.isEmpty) {
                                notification = Notification.show(
                                    "Wypełnij wszsytkie pola, aby dodać osobę!",
                                    5000,
                                    Notification.Position.MIDDLE
                                )
                                notification.addThemeVariants(NotificationVariant.LUMO_ERROR)
                            } else {
                                Person(nameField.value, ageField.value, statusField.value).save()
                                notification =
                                    Notification.show("Dodano nową osobę!", 5000, Notification.Position.BOTTOM_END)
                                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS)
                            }
                            nameField.clear()
                            ageField.clear()
                            statusField.clear()
                            grid.refresh()
                        }
                    }
                    accept.addThemeVariants(ButtonVariant.LUMO_PRIMARY)
                    cancel = button("Zamknij")
                    cancel.addClickListener {
                        dialog.close()
                    }
                }
            }


            dialogButton = button("Dodaj osobę") {
                onLeftClick {
                    dialog.open()
                    val notification: Notification =
                        Notification.show(
                            "Otworzono okno do zapisu nowej osoby!",
                            3000,
                            Notification.Position.BOTTOM_END
                        )
                    notification.addThemeVariants(NotificationVariant.LUMO_CONTRAST)
                }
            }
            dialogButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS)

            routerLink(text = "Strona główna", viewType = HelloView::class)
        }
    }

    private fun getGuests(): Int {
        val guests = Person.findAll()
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