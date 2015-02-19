(function(){
    'use strict';
    angular
        .module('eightyControllers')
        .controller('customersCtrl', customersCtrl);

    customersCtrl.$inject = ['$scope', 'customersFactory', 'customersList'];

    function customersCtrl($scope, customersFactory, customersList) {
        /*jshint validthis:true*/
        var vm = this;
        vm.customers = customersList;

        $scope.$on('topTags-update', function() {
            customersFactory.getCustomers().then(function (customers) {
                vm.customers = customers;
            });
        });
    }
})();