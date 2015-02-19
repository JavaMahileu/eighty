/**
 * @ngdoc service
 * @name eightyUtilityFactory.utility
 *
 * @description
 * A factory which works with eightyStore
 */
(function () {
  'use strict';
    angular
        .module('eightyFactories')
        .factory('utility', utility);

    utility.$inject = ['eightyStore'];

    function utility(eightyStore) {

        return {
            containsInSet: ifContainsInSet,
            ifExists: ifExists,
            getLength: getStoreLength,
            getSet: getSet,
            eraseSet: eraseSet,
            saveInSet: saveInSet,
            removeFromSet: removeFromSet,
            updateInSet: updateInSet
        };

        function ifContainsInSet(key, obj) {
            var set = eightyStore.get(key);
            if (set && set.length > 0) {
                return containsInSet(set, obj);
            }
            return false;
        }
        function ifExists(key) {
            var flag = false;
            var set = eightyStore.get(key);
            if (set && set.length > 0) {
                flag = true;
            }
            return flag;
        }
        function getStoreLength(key) {
            var len = 0;
            var set = eightyStore.get(key);
            if (set && set.length > 0) {
                len = set.length;
            }
            return len;
        }
        function getSet(key) {
            var set = eightyStore.get(key);
            if (set && set.length > 0) {
                return set;
            }
            return [];
        }
        function eraseSet(key) {
            eightyStore.remove(key);
        }
        function saveInSet(key, obj) {
            var set = eightyStore.get(key);
            if (set === null) {
                set = [];
            }
            set.push(obj);
            eightyStore.set(key, set);
        }
        function removeFromSet(key, obj) {
            var set = eightyStore.get(key);
            set.splice(getIndex(set, obj), 1);
            eightyStore.set(key, set);
        }
        function updateInSet(key, obj) {
            var set = eightyStore.get(key);
            set[getIndex(set, obj)] = obj;
            eightyStore.set(key, set);
        }
    }
})();
