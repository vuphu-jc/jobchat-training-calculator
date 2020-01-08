package com.example.calculator.domain

interface Base {
    interface UseCase<P,R> {
        fun run(params: P, response: R)
    }
}