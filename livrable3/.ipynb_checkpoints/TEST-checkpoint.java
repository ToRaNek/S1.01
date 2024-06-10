class test extends Program{
    
    void toString(String[] tab){
        for(int cpt=0; cpt<length(tab);cpt++){
            println(tab[cpt]);
        }
    }
    
    void algorithm() {
    toString(getAllFilesFromDirectory("data"));
    }
    
}