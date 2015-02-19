/**
 * @ngdoc service
 * @name eightyModalDataFactory.modalData
 *
 * @description
 * A service which shares data about modal window between controllers.
 */
(function () {
    'use strict';
    angular
        .module('eightyFactories')
        .factory('modalData', modalData);

    function modalData() {

        var modalWindow = {
            shouldBeOpen: false
        };
        return {
            getShouldBeOpen: getShouldBeOpen,
            setShouldBeOpen: setShouldBeOpen
        };
        function getShouldBeOpen() {
            return modalWindow.shouldBeOpen;
        }
        function setShouldBeOpen(value) {
            modalWindow.shouldBeOpen = value;
        }
    }
})();