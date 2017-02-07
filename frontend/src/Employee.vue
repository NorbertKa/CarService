<template>
    <div id="employee">
        <h2>{{employee.name}} {{employee.surname}}</h2>
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
        employee() {
            return this.$store.getters.employee;
        },
        cars() {
            return this.$store.getters.cars;
        },
        timeframes() {
            return this.$store.getters.timeframes;
        }
    }
}
</script>

<style>

</style>