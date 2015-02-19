/**
 * @ngdoc service
 * @name eightyStoreFactory.eightyStore
 *
 * @description
 * A factory which works with cookie resource.
 */
(function () {
   'use strict';
    angular
        .module('eightyFactories')
        .factory('eightyStore', eightyStore);

    eightyStore.$inject = ['$window'];

    /**
     * @name $eightyStore
     * @description Application storage
     */
    function eightyStore($window) {
        /**
         * Global Vars
         */
        var storage = (typeof $window.localStorage === 'undefined') ? undefined : $window.localStorage,
            supported = !(typeof storage === 'undefined' || typeof $window.JSON === 'undefined');

        var privateMethods = {
            parseValue: parseValue
        };

        var publicMethods = {
            set: set,
            get: get,
            remove: remove
        };
        return publicMethods;

        /**
         * @name parseValue
         * @description Pass any type of a string from the localStorage to be parsed so it returns a usable version (like an Object)
         * @param {String} res - a string that will be parsed for type
         * @returns {*} - whatever the real type of stored value was
         */
        function parseValue(res) {
            var val;
            try {
                val = JSON.parse(res);
                if (typeof val === 'undefined') {
                    val = res;
                }
                if (val === 'true') {
                    val = true;
                }
                if (val === 'false') {
                    val = false;
                }
                if (parseFloat(val) === val && !angular.isObject(val)) {
                    val = parseFloat(val);
                }
            } catch (e) {
                val = res;
            }
            return val;
        }

        /**
         * @name set
         * @description Set - let's you set a new localStorage key pair set
         * @param key {String} - a string that will be used as the accessor for the pair
         * @param value {Object} - the value of the localStorage item
         * @returns {*} - will return whatever it is you've stored in the local storage
         */
        function set(key, value) {
            if (!supported) {
                try {
                    $.cookie(key, value);
                    return value;
                } catch (e) {
                    console.log('Local Storage not supported, make sure you have the $.cookie supported.');
                }
            }
            var saver = JSON.stringify(value);
            storage.setItem(key, saver);
            return privateMethods.parseValue(saver);
        }

        /**
         * @name get
         * @description Get - let's you get the value of any pair you've stored
         * @param key {String} - the string that you set as accessor for the pair
         * @returns {*} - Object,String,Float,Boolean depending on what you stored
         */
        function get(key) {
            if (!supported) {
                try {
                    return privateMethods.parseValue($.cookie(key));
                } catch (e) {
                    console.log('Local Storage not supported, make sure you have the $.cookie supported.');
                    return null;
                }
            }
            var item = storage.getItem(key);
            return privateMethods.parseValue(item);
        }

        /**
         * @name remove
         * @description Remove - let's you nuke a value from localStorage
         * @param key {String} - the accessor value
         * @returns {boolean} - if everything went as planned
         */
        function remove(key) {
            if (!supported) {
                try {
                    $.cookie(key, null);
                    return true;
                } catch (e) {
                    console.log('Local Storage not supported, make sure you have the $.cookie supported.');
                    return false;
                }
            }
            storage.removeItem(key);
            return true;
        }
    }
})();