class GenerateurSite extends Program {
    
    final char NEW_LINE = '\n';
    final String ENTETE = "<!DOCTYPE html>" + NEW_LINE +
                          "<html lang=\"fr\">" + NEW_LINE;
    final int IDX_NOM = 0;
    final int IDX_DATE = 1;
    final int IDX_ENTREPRISE = 2;
    final int IDX_PRIX = 3;
    final int IDX_DESCRIPTION = 4;

    // Livrable 1
    String rechercherValeur(String chaine, String cle) {
        String valeur = "";
        int indice = 0;
        while (indice < length(chaine) && indice+length(cle) < length(chaine) && 
               !equals(cle, substring(chaine, indice, indice+length(cle)))) {
            indice = indice + 1;
        }
        if (indice < length(chaine)-length(cle)) {
            int indiceRetourLigne = indice;
            while (indiceRetourLigne < length(chaine) && charAt(chaine, indiceRetourLigne) != NEW_LINE) {
                indiceRetourLigne = indiceRetourLigne + 1;
            }
            valeur = substring(chaine, indice+length(cle), indiceRetourLigne);
        }
        return valeur;
    }

    String genererHead(String titre) {
        return 
            "  <head>" + NEW_LINE + 
            "    <title>" + titre + "</title>" + NEW_LINE + 
            "    <meta charset=\"utf-8\">" + NEW_LINE +  
            "    <link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">" + NEW_LINE +  
            "  </head>" + NEW_LINE;
    }

    String genererHeader(String titre) {
        return
            "    <header>" + NEW_LINE +
            "      <h1>" + titre + "</h1>" + NEW_LINE +
            "    </header>" + NEW_LINE;
    }

    String genererNav(int x) {
        String rendu =
            "    <nav>"+ NEW_LINE +
            "      <ul>" + NEW_LINE +
            "        <li><a href=\"index.html\">Accueil</a></li>" + NEW_LINE +
            "        <li><a href=\"produit"+ x +".html\">"+ rechercherValeur(fileAsString("data/produit" + (x) +".txt"),"nom : ") +"</a></li>" + NEW_LINE;

            if(x+5<length(getAllFilesFromDirectory("data"))){
            rendu +="        <li><a href=\"produit"+ (x+1) +".html\">"+ rechercherValeur(fileAsString("data/produit" + (x+1) +".txt"),"nom : ") +"</a></li>" + NEW_LINE +
                    "        <li><a href=\"produit"+ (x+2) +".html\">"+ rechercherValeur(fileAsString("data/produit" + (x+2) +".txt"),"nom : ") +"</a></li>" + NEW_LINE +
                    "        <li><a href=\"produit"+ (x+3) +".html\">"+ rechercherValeur(fileAsString("data/produit" + (x+3) +".txt"),"nom : ") +"</a></li>" + NEW_LINE +
                    "        <li><a href=\"produit"+ (x+4) +".html\">"+ rechercherValeur(fileAsString("data/produit" + (x+4) +".txt"),"nom : ") +"</a></li>" + NEW_LINE;
            }else{
                int diff = 31-x;
                for (int i = x+1 ; i<x+diff ; i++)
                rendu +="        <li><a href=\"produit"+ i +".html\">"+ rechercherValeur(fileAsString("data/produit" + (x+1) +".txt"),"nom : ") +"</a></li>" + NEW_LINE;
            }
        rendu +=
            "      </ul>" + NEW_LINE +
            "    </nav>" + NEW_LINE;
        return rendu;
    }

    // Livrable 1
    String genererContenuProduit(String nom, String date, String entreprise, String prix, String description) {
        return             
            "    <main>" + NEW_LINE +
            "      <section>" + NEW_LINE +
            "        <h2>" + nom + " (" + entreprise + ")</h2>" + NEW_LINE +
            "        <h3>" + prix + " (Sortie en " + date + ")</h3>" + NEW_LINE +
            "        <p>" + NEW_LINE +
            description + NEW_LINE + 
            "        </p>" + NEW_LINE + 
            "      </section>" + NEW_LINE +
            "    </main>" + NEW_LINE;
    }

    String genererPageProduit(String nomFichier, String head_titre, int numnav) {
        final String CONTENU     = fileAsString(nomFichier);
        final String NOM         = rechercherValeur(CONTENU, "nom : ");
        final String DATE        = rechercherValeur(CONTENU, "date : ");
        final String ENTREPRISE  = rechercherValeur(CONTENU, "entreprise : ");
        final String PRIX        = rechercherValeur(CONTENU, "prix : ");
        final String DESCRIPTION = rechercherValeur(CONTENU, "description : ");

        return 
            ENTETE + 
            genererHead(head_titre) + 
            "  <body>" + NEW_LINE + 
            genererHeader(head_titre) + 
            genererNav(numnav) + 
            genererContenuProduit(NOM, DATE, ENTREPRISE, PRIX, DESCRIPTION) + 
            "  </body>" + NEW_LINE + 
            "</html>" ;
    }

    String genererAccueil(String head_titre) {
        return ENTETE +
            genererHead(head_titre) +
            "  <body>" + NEW_LINE + 
            genererHeader(head_titre) + 
            genererNav(1) +
            "    <main>" + NEW_LINE +
            "      <section>" + NEW_LINE +
            "        <h2>Tout ce que vous avez toujours voulu savoir sur les vieux ordis sans jamais avoir osé le demander !</h2>" + NEW_LINE +
            "          <p>" + NEW_LINE +
            "Bienvenue dans le musée virtuel d'ordinateurs mythiques de l'histoire de l'informatique. "+ 
            "Vous trouverez ici des éléments sur quelques machines qui ont marqué l'histoire de l'informatique "+
            "que cela soit par leurs caractéristiques techniques ou l'impact commercial qu'elles ont eu et qui "+
            "ont contribué au développement du secteur informatique." + NEW_LINE +
            "          </p>" + NEW_LINE +
            "      </section>" + NEW_LINE +
            "    </main>" + NEW_LINE +
            "  </body>" + NEW_LINE + 
            "</html>" +NEW_LINE;
    }

    String[][] chargerProduits(String repertoire, String prefixe){
        String[] test = getAllFilesFromDirectory(repertoire);
        String[][] tab2 = new String [length(test)][5];
        for (int i=1 ; i<length(test) ; i++){
            String contenu = fileAsString(repertoire + '/' + prefixe + (i) +".txt");
            tab2 [i][IDX_NOM] =rechercherValeur(contenu,"nom : ");
            tab2 [i][IDX_DATE] =rechercherValeur(contenu,"date : ");
            tab2 [i][IDX_ENTREPRISE] =rechercherValeur(contenu,"entreprise : ");
            tab2 [i][IDX_PRIX] =rechercherValeur(contenu,"prix : ");
            tab2 [i][IDX_DESCRIPTION] =rechercherValeur(contenu,"description : ");
        }

        return tab2;
    }

    
    String toString(String[][] produits){
        String rendu = "";
        for (int i =0 ; i<length(produits,1) ; i++){
            rendu += produits[i][IDX_NOM] +
                    " ("+ produits[i][IDX_DATE] + ") - " +
                    produits[i][IDX_PRIX] +
                    " - " + produits[i][IDX_DESCRIPTION] + NEW_LINE + NEW_LINE;
        }
        return rendu;
    }

    void algorithm() {
        int x = 1;
        print(toString(chargerProduits("data","produit")));
        println();
        println();
        println();
        println();
        println();
        println();
        println();
        println();
        println("Génération de la page 'index.html'");
        final String TITLE = "Ordinateurs mythiques";
        final String PAGE_ACCUEIL = genererAccueil(TITLE);
        stringAsFile("output/index.html", PAGE_ACCUEIL);
        //println(PAGE_ACCUEIL);
        int taille = length(getAllFilesFromDirectory("data"));
        // Création des pages produits
        for (int nb = 1; nb <= taille; nb = nb + 1) {
            final String SOURCE = "data/"   + "produit" + nb + ".txt";
            final String CIBLE  = "output/" + "produit" + nb + ".html";
            println("Génération de la page '"+ CIBLE + "' depuis le fichier source '" + SOURCE + "'");
            final String PAGE_PRODUIT = genererPageProduit(SOURCE, TITLE, x);
            x++;
            stringAsFile(CIBLE, PAGE_PRODUIT);
            //println(PAGE_PRODUIT);
        }
    }
}