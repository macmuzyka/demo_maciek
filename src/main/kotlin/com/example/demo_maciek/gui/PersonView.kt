package com.example.demo_maciek.gui

import com.example.demo_maciek.model.Person
import com.example.demo_maciek.model.Status
import com.github.mvysny.karibudsl.v10.*
import com.github.mvysny.kaributools.refresh
import com.github.vokorm.dataloader.dataLoader
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
    private lateinit var dialog: Dialog

    val root = ui {
        verticalLayout {
            h1("PERSON VIEW")

            val grid = grid<Person> {
                isExpand = true
                columnFor(Person::id)
                columnFor(Person::name)
                columnFor(Person::age)
                columnFor(Person::status)
                dataProvider = Person.dataLoader.asDataProvider()
            }

            val form = horizontalLayout {
                nameField = textField("Name:")
                ageField = integerField("Age:")
                statusField = comboBox("Status") {
                    setItems(*Status.values())
                }
            }

            button("Add person..") {
                onLeftClick {
//                    dialog.open()
                    val notification: Notification
                    if (nameField.isEmpty || ageField.isEmpty || statusField.isEmpty) {
                        notification = Notification.show("Unable to add new person, not all data has been filled!", 5000, Notification.Position.MIDDLE)
                        notification.addThemeVariants(NotificationVariant.LUMO_ERROR)
                    } else {
                        Person(nameField.value, ageField.value, statusField.value).save()
                        notification = Notification.show("New person added!", 5000, Notification.Position.BOTTOM_END)
                        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS)
                    }
                    nameField.clear()
                    ageField.clear()
                    statusField.clear()
                    grid.refresh()
                }
            }

            routerLink(text = "Back to main", viewType = HelloView::class).blur()
        }
    }
}