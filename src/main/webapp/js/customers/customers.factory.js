(function(){
    'use strict';
    /*exported isPrintLog */
    angular
        .module('eightyFactories')
        .factory('customersFactory', customersFactory);

    customersFactory.$inject = ['crudFactory'];

    function customersFactory(crudFactory) {

        var publicMethod = {
            getCustomers: getCustomers
        };
        return publicMethod;

        function getCustomers() {
            var customers = crudFactory.customers().get().$promise;
            customers.then(undefined, function (error) {
                printLog(error);
            });
            return customers;
        }
    }
})();