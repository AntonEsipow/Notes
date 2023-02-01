package com.bigtoapp.notes.main.presentation

interface NavigationCommunication {

    interface Observe: Communication.Observe<NavigationStrategy>

    interface Mutate: Communication.Put<NavigationStrategy>

    interface Mutable: Observe, Mutate

    class Base: Communication.SingleUi<NavigationStrategy>(), Mutable
}