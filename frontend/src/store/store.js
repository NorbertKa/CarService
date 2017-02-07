import Vue from 'vue'
import Vuex from 'vuex'

import State from './state'

Vue.use(Vuex);

export default new Vuex.Store({
    state: State,
    mutations: {
        SET_ALERT(state, alert) {
            state.alert = alert;
        },
        SET_EMPLOYEES(state, employees) {
            state.employees = employees;
        },
        SET_EMPLOYEE(state, employee) {
            state.employee = employee;
        },
        SET_CARS(state, cars) {
            state.cars = cars;
        },
        SET_CAR(state, car) {
            state.car = car;
        },
        SET_EMPLOYEECARS(state, employeeCars) {
            state.employeeCars = employeeCars;
        },
        SET_TIMEFRAMES(state, timeframes) {
            console.log(timeframes);
            state.timeframes = timeframes;
        }
    },
    actions: {
        getEmployees({commit}) {
            Vue.http.get('http://127.0.0.1:1337/employees')
                .then(response => {
                    response.json().then(data => {
                        commit('SET_EMPLOYEES', data);
                    })
                })
        },
        getEmployee({commit}, id) {
            Vue.http.get('http://127.0.0.1:1337/employees/' + id)
                .then(response => {
                    response.json().then(data => {
                        commit('SET_EMPLOYEE', data);
                    })
                })
        },
        getEmployeeInfo({commit}, id) {
            Vue.http.get('http://127.0.0.1:1337/employees/' + id)
                .then(response => {
                    response.json().then(data => {
                        commit('SET_EMPLOYEE', data);
                        Vue.http.get('http://127.0.0.1:1337/employees/' + id + "/cars")
                            .then(response => {
                                response.json().then(data => {
                                    commit('SET_EMPLOYEECARS', data);
                                    let employeeCarIds = data;
                                    let cars = [];
                                    data.forEach(function(value) {
                                        Vue.http.get('http://127.0.0.1:1337/cars/' + value.carId)
                                            .then(response => {
                                                response.json().then(data => {
                                                    cars.push(data);
                                                    commit('SET_CARS', cars);
                                                })
                                            })
                                    });
                                    let timeframes = [];
                                    employeeCarIds.forEach(function(value) {
                                        Vue.http.get('http://127.0.0.1:1337/timeframes?employeeCarId=' + value.id)
                                            .then(response => {
                                                response.json().then(data => {
                                                    timeframes = timeframes.concat(data);
                                                    commit('SET_TIMEFRAMES', timeframes);console.log(timeframes)
                                                })
                                            })
                                    });
                                })
                            })
                    })
                })
        },
        getCars({commit}) {
            Vue.http.get('http://127.0.0.1:1337/cars')
                .then(response => {
                    response.json().then(data => {
                        commit('SET_CARS', data);
                    })
                })
        },
        getCar({commit}, id) {
            Vue.http.get('http://127.0.0.1:1337/car/' + id)
                .then(response => {
                    response.json().then(data => {
                        commit('SET_CAR', data);
                    })
                })
        }
    },
    getters: {
        alert: state => state.alert,
        employees: state => state.employees,
        cars: state => state.cars,
        employeeCars: state => state.employeeCars,
        timeframes: state => state.timeframes,
        employee: state => state.employee,
        car: state => state.car
    }
})