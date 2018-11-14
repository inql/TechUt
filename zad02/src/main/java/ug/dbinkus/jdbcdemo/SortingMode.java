package ug.dbinkus.jdbcdemo;

public enum SortingMode {
    ASCENDING{
        public String toString(){
            return "ASC";
        }
    },
    DESCENDING{
        public String toString(){
            return "DESC";
        }
    }
}
