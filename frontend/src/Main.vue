<template>
    <div id="main">
        <div class="row">
            <div class="employee-cell two columns">
                <ul v-for="employee in employees">
                    <li class="mdl-list__item">
                        <h5><span v-on:click="selectEmployee(employee)" class="employee-item">
                        {{employee.name}}  {{employee.surname}}
                        </span></h5>
                    </li>
                </ul>
                <form>
                    <input v-model="name" type="text" placeholder="name" />
                    <input v-model="surname" type="text" placeholder="surname" />
                    <input @click="insertEmployee()" type="button" value="insert" />
                </form>

                <form>
                    <input v-model="manufacturer" type="text" placeholder="manufacturer" />
                    <input v-model="model" type="text" placeholder="model" />
                    <input v-model="regNumber" type="text" placeholder="regNumber" />
                    <input @click="insertCar()" type="button" value="insert" />
                </form>
                <div v-if="selectedEmployee.name">
                <h5>{{selectedEmployee.name}} {{selectedEmployee.surname}}</h5>
                <h5>{{selectedCar.regNumber}}</h5>
                <input @click="combineEmployeeCar()" type="button" value="Combine" />
                </div>
            </div>
            <div class="eight columns car-column">
                <div v-for="car in cars">
                    <div class="car-card mdl-card mdl-shadow--2dp">
                      <div class="mdl-card__title mdl-card--expand">
                        <h4 v-on:click="selectCar(car)" class="center car-number">
                          {{car.regNumber}}
                        </h4>
                        <h6 class="center">
                          {{car.manufacturer}}</br>
                          {{car.model}}
                        </h6>
                      </div>
                      </div>
                    </div>
                </div>
             </div>
        </div>
    </div>
</template>

<script>
export default {

    name: 'app',
    data () {
        return {
            name: "",
            surname: "",
            manufacturer: "",
            model: "",
            regNumber: "",
            selectedEmployee: {
                name: "",
                surname: "",
            },
            selectedCar: {
                regNumber: ""
            }
        }
    },
    methods: {
        selectEmployee(e){
            this.selectedEmployee = e
        },
        selectCar(e){
            this.selectedCar = e
        },
        insertEmployee(){
            this.$http.post('http://127.0.0.1:1337/employees',{
                name: this.name,
                surname: this.surname
            },{emulateJSON: true}).then((response) => {
                response.json().then(data => {
                    console.log(data);
                    this.$store.dispatch('getEmployees');
                })
            })
        },
        insertCar(){
            this.$http.post('http://127.0.0.1:1337/cars',{
                manufacturer: this.manufacturer,
                model: this.model,
                regNumber: this.regNumber
            },{emulateJSON: true}).then((response) => {
                response.json().then(data => {
                    console.log(data);
                    this.$store.dispatch('getCars');
                })
            })
        },
        combineEmployeeCar(){
            this.$http.post('http://localhost:1337/employeecars',{
                employeeId: this.selectedEmployee.id,
                carId: this.selectedCar.id
            },{emulateJSON: true}).then((response) => {
                response.json().then(data => {
                    console.log(data);
                    this.$store.dispatch('getCars');
                })
            })
        }
    },
    computed: {
        employees() {
            return this.$store.getters.employees;
        },
        cars() {
            return this.$store.getters.cars;
        }
    }
}
</script>

<style>
.car-card {

}

.car-column > * {
    padding: 0.7em;
    border: 1px solid black;
    margin-left: 1em;
    margin-bottom: 1em;
    display: inline-block;
    min-width: 8em;
}

.center {
    text-align: center;
}

.car-number {
    color: darkgreen;
}

.car-number:hover{
    color: red;
    cursor: pointer;
}

.employee-item {
    color: darkgreen;
}

.employee-item:hover {
    cursor: pointer;
    color: red;
}

ul {
    list-style-type: none;
}

input {
    border: 1px solid black !important;
    border-radius: 0 !important;
    width: 12em !important;
}

input[type="text"] {
    color: black !important;
}
</style>