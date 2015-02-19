(function(){
    'use strict';
    angular
        .module('eightyControllers')
        .controller('deleteQuestionInstanceCtrl', deleteQuestionInstance);

    deleteQuestionInstance.$inject = ['$scope'];
    function deleteQuestionInstance ($scope) {
        /*jshint validthis:true*/
        var vm = this;

        vm.confirmDeleteQuestion = function() {
            $scope.$close();
        };
        vm.cancelDeleteQuestion = function() {
            $scope.$dismiss();
        };
    }
})();