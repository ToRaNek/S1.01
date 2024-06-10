class GenerateurSite extends Program {
    
    final char   NEW_LINE = '\n';
    final String ENTETE   = "<!DOCTYPE html>"    + NEW_LINE +
                            "<html lang=\"fr\">" + NEW_LINE;
    
    // indices des différents champs (en colonne) de la structure de données des produits
    final int IDX_NOM         = 0;
    final int IDX_DATE        = 1;
    final int IDX_ENTREPRISE  = 2;
    final int IDX_PRIX        = 3;
    final int IDX_DESCRIPTION = 4;

    // Livrable 1
    String rechercherValeur(String chaine, String cle) {
        String valeur = "";
        int indice = 0;
        while (indice < length(chaine) && indice + length(cle) < length(chaine) && 
               !equals(cle, substring(chaine, indice, indice+length(cle)))) {
            indice = indice + 1;
        }
        if (indice < length(chaine) - length(cle)) {
            int indiceRetourLigne = indice;
            while (indiceRetourLigne < length(chaine) && charAt(chaine, indiceRetourLigne) != NEW_LINE) {
                indiceRetourLigne = indiceRetourLigne + 1;
            }
            valeur = substring(chaine, indice+length(cle), indiceRetourLigne);
        }
        return valeur;
    }

    // Livrable 2
    String genererHead(String titre) {
        return 
            "  <head>"    + NEW_LINE + 
            "    <title>" + titre + "</title>" + NEW_LINE + 
            "    <meta charset=\"utf-8\">"     + NEW_LINE +  
            "    <link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">" + NEW_LINE +  
            "  </head>"    + NEW_LINE;
    }

    // Livrable 2
    String genererHeader(String titre) {
        return
            "    <header>"  + NEW_LINE +
            "      <h1>" + titre + "</h1>" + NEW_LINE +
            "    </header>" + NEW_LINE;
    }

    /**
     * La fonction genererNav est paramétrée par la table des produits et le numéro du produit 
     * de la page courante. Ainsi, la navigation propose en premier bouton vers l'accueil, puis
     * le bouton vers le produit courant (connu grâce au paramètre numeroProduit) et ensuite 4
     * boutons vers les produits suivants. Si l'on arrive à la fin du catalogue, il faut être 
     * attentif à ne générer que le nombre de boutons nécessaires. Chaque bouton contient 
     * maintenant le nom du produit au lieu du nommage générique "ProduitX" du livrable prédécent. 
     */
    String genererNav(String[][] produits, int numeroProduit) {
        String boutonsProduits = "";
        int nbBoutons = 1;
        while (nbBoutons <= 5 && numeroProduit + nbBoutons <= length(produits, 1)) {
            boutonsProduits = boutonsProduits + 
                "        <li><a href=\"produit" + (numeroProduit + nbBoutons) + ".html\">" +
                getChamps(produits, numeroProduit + nbBoutons - 1, IDX_NOM) + "</a></li>" + NEW_LINE;
            nbBoutons = nbBoutons + 1;
        }
        return 
            "    <nav>"     + NEW_LINE +
            "      <ul>"    + NEW_LINE +
            "        <li><a href=\"index.html\">Accueil</a></li>" + NEW_LINE +
            boutonsProduits + 
            "<li><a href=\"produits-nom.html\">Produits</a></li>\n"+
            "      </ul>"   + NEW_LINE +
            "    </nav>"    + NEW_LINE;
    }

    // Livrable 1
    String genererContenuProduit(String IDX_NOM, String IDX_DATE, String IDX_ENTREPRISE, String IDX_PRIX, String IDX_DESCRIPTION) {
        return             
            "    <main>"       + NEW_LINE +
            "      <section>"  + NEW_LINE +
            "        <h2>" + IDX_NOM + " (" + IDX_ENTREPRISE + ")</h2>"      + NEW_LINE +
            "        <h3>" + IDX_PRIX + " (Sortie en " + IDX_DATE + ")</h3>" + NEW_LINE +
            "        <p>"      + NEW_LINE +
            IDX_DESCRIPTION        + NEW_LINE + 
            "        </p>"     + NEW_LINE + 
            "      </section>" + NEW_LINE +
            "    </main>"      + NEW_LINE;
    }

    // Livrable 2
    String genererPageProduit(String head_titre, String[][] produits, int numeroProduit) {
        String NOM         = getChamps(produits, numeroProduit, IDX_NOM);
        String DATE        = getChamps(produits, numeroProduit, IDX_DATE);
        String ENTREPRISE  = getChamps(produits, numeroProduit, IDX_ENTREPRISE);
        String PRIX        = getChamps(produits, numeroProduit, IDX_PRIX);
        String DESCRIPTION = getChamps(produits, numeroProduit, IDX_DESCRIPTION);
        return 
            ENTETE + 
            genererHead(head_titre) + 
            "  <body>"  + NEW_LINE + 
            genererHeader(head_titre) + 
            genererNav(produits, numeroProduit) + 
            genererContenuProduit(NOM, DATE, ENTREPRISE, PRIX, DESCRIPTION) + 
            "  </body>" + NEW_LINE + 
            "</html>";
    }

    // Livrable 2
    String genererAccueil(String head_titre, String[][] produits) {
        return ENTETE +
            genererHead(head_titre)   +
            "  <body>"                + NEW_LINE + 
            genererHeader(head_titre) + 
            genererNav(produits, 0)   +
            "    <main>"              + NEW_LINE +
            "      <section>"         + NEW_LINE +
            "        <h2>Tout ce que vous avez toujours voulu savoir sur les vieux ordis sans jamais avoir osé le demander !</h2>" + NEW_LINE +
            "          <p>"           + NEW_LINE +
            "Bienvenue dans le musée virtuel d'ordinateurs mythiques de l'histoire de l'informatique. "+ 
            "Vous trouverez ici des éléments sur quelques machines qui ont marqué l'histoire de l'informatique "+
            "que cela soit par leurs caractéristiques techniques ou l'impact commercial qu'elles ont eu et qui "+
            "ont contribué au développement du secteur informatique." + NEW_LINE +
            "          </p>"          + NEW_LINE +
            "      </section>"        + NEW_LINE +
            "    </main>"             + NEW_LINE +
            "  </body>"               + NEW_LINE + 
            "</html>"                 + NEW_LINE;
    }

    /**
     * La fonction chargerProduits parcourt les fichiers 'repertoire/prefixeX.txt' et retourne
     * un tableau à deux dimensions de chaîne de caractères contenant l'ensemble des produits.
     * Le tableau comporte 5 colonnes, la première pour le IDX_NOM du produit, la deuxième pour 
     * le IDX_DATE du produit, la troisième pour l'IDX_ENTREPRISE du produit, la quatrième pour 
     * le IDX_PRIX du produit et la cinquième pour la IDX_DESCRIPTION du produit. Le tableau comporte 
     * donc autant de lignes qu'il y a de fichiers produits présents dans le répertoire 'data'.
     */
    String[][] chargerProduits(String repertoire, String prefixe) {
        final String[] FICHIERS = getAllFilesFromDirectory(repertoire);
        int nbFichiersProduits = 0;
        for (int idx=0; idx < length(FICHIERS); idx++) {
            if (length(FICHIERS[idx]) > 8 && equals("produit",  substring(FICHIERS[idx], 0, 7))) {
                nbFichiersProduits = nbFichiersProduits + 1;
            }
        }
        println();
        String[][] produits   = new String[nbFichiersProduits][5];
        for (int idx = 0; idx < length(produits, 1); idx = idx + 1) {
            final String CONTENU           = fileAsString(repertoire + "/" + prefixe + (idx+1) + ".txt");
            produits[idx][IDX_NOM]         = rechercherValeur(CONTENU, "nom : ");
            produits[idx][IDX_DATE]        = rechercherValeur(CONTENU, "date : ");
            produits[idx][IDX_ENTREPRISE]  = rechercherValeur(CONTENU, "entreprise : ");
            produits[idx][IDX_PRIX]        = rechercherValeur(CONTENU, "prix : ");
            produits[idx][IDX_DESCRIPTION] = rechercherValeur(CONTENU, "description : ");
        }
        return produits;
    }

    /**
     * La fonction getChamps prend un paramètre un tableau de produits, un indice de ligne 
     * (correspondant à un produit) et un indice de colonne correspondant à l'un des 
     * champs IDX_NOM, IDX_DATE, IDX_ENTREPRISE, IDX_PRIX, IDX_DESCRIPTION et retourne la valeur correspondant 
     * au champs souhaité. En cas d'indices invalides, la fonction se termine sur une 
     * exception de type ArrayIndexOutOfBoundsException.
    */
    String getChamps(String[][] produits, int idxLigne, int idxChamps) {
        return produits[idxLigne][idxChamps];
    }

    /**
     * La fonction toString retourne une représentation sous forme de chaîne de caractères
     * du tableau de produits à des fins de déboggage. Ainsi, la fonction ne retourne qu'un
     * sous-ensemble des données : IDX_NOM (IDX_DATE) - IDX_PRIX - IDX_DESCRIPTION. Exemple de chaîne produite :
     * Apple II (Avril 1977) - 1 298 dollars (environ 5 600 dollars ajustés à l'inflation en 
     * 2023) - L'Apple II était l'un des premiers ordinateurs personnels largement adoptés par 
     * le grand public. Il était équipé d'un microprocesseur MOS Technology 6502, de 4 Ko de RAM 
     * (extensible à 48 Ko) et d'une couleur graphique. L'Apple II a été très populaire dans 
     * les écoles et les foyers.
     */
    String toString(String[][] produits) {
        String table = "";
        for (int idxLigne = 0; idxLigne < length(produits, 1); idxLigne = idxLigne + 1) {
            table = table + getChamps(produits, idxLigne, IDX_NOM)  + " ("   + 
                            getChamps(produits, idxLigne, IDX_DATE) + ") - " +
                            getChamps(produits, idxLigne, IDX_PRIX) + " - "  + 
                            getChamps(produits, idxLigne, IDX_DESCRIPTION)   + '\n';
        }
        return table;
    }

    String[][] permuterLignes(String[][] tab,int indiceachanger, int indiceamettre) {
        for(int i = 0; i <length(tab[0]); i++ ) {
            String temp = tab[indiceachanger][i];
            tab[indiceachanger][i] = tab[indiceamettre][i];
            tab[indiceamettre][i] = temp;
        }
        return tab;
    }

    void testPermuterLignes() {
    String[][] t = new String[][]{{"a", "b", "c"},
    {"D", "E", "F"}};
    permuterLignes(t, 0, 1);
    assertArrayEquals(new String[][]{{"D", "E", "F"},
    {"a", "b", "c"}}, t);
    }

    String[][] trierSurColonne(String[][] tab, int colonneatrier) {
        int borneMax = length(tab)-1;
        while(borneMax>0) {
            int max = 0;
            for(int i =0; i<=borneMax; i++) {
                if(compare(tab[i][colonneatrier],tab[max][colonneatrier]) > 0) {
                    max = i;
                }
            }
            permuterLignes(tab,max,borneMax);
            borneMax--;
        }
        return tab;
    }

    void testTrierSurColonne() {
String[][] t = new String[][]{{"a1", "b3", "c2"},
{"a3", "b2", "c3"},
{"a2", "b1", "c1"}};
trierSurColonne(t, 0); // on trie sur la première colonne
assertArrayEquals(new String[][]{{"a1", "b3", "c2"},
{"a2", "b1", "c1"},
{"a3", "b2", "c3"}}, t);
trierSurColonne(t, 1); // on trie sur la deuxième colonne
assertArrayEquals(new String[][]{{"a2", "b1", "c1"},
{"a3", "b2", "c3"},
{"a1", "b3", "c2"}}, t);
trierSurColonne(t, 2); // on trie sur la troisième colonne
assertArrayEquals(new String[][]{{"a2", "b1", "c1"},
{"a1", "b3", "c2"},
{"a3", "b2", "c3"}}, t);
    }

    void GenererListeProduit() {
        String[] nomparametre = new String[]{"nom", "entreprise", "date", "prix", "description"};
        String[][] TableauProduit = chargerProduits("data", "produit");
        for(int i=0; i<length(nomparametre);i++) {
            String[][] tabtrier = trierSurColonne(TableauProduit, i);
            String content = "<!DOCTYPE html>\n" +
            "<html lang=\"fr\">\n" +
            "  <head>\n" +
            "    <title>Ordinateurs mythiques</title>\n" +
            "    <meta charset=\"utf-8\">\n" +
            "    <link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">\n" +
            "  </head>\n"+
            "  <body>\n" +
            "    <header>\n" +
            "       <h1>Ordinateurs mythiques</h1>\n" +
            "   </header>\n"+
            "    <nav>\n" +
            "      <ul>\n"+
            "        <li><a href=\"index.html\">Accueil</a></li>\n"+
            "<li><a href=\"produits-nom.html\">Produits</a></li>\n" +
            "      </ul>\n"+
            "    </nav>\n"+
            "    <main>\n"+
            "      <section>\n"+
            "        <h2>Liste de l'ensemble des ordinateurs</h2>\n"+
            "          <p>\n"+
            "Trier sur : <a href=\"produits-nom.html\">NOM</a>, <a href=\"produits-date.html\">DATE</a>, <a href=\"produits-prix.html\">PRIX</a>, <a href=\"produits-entreprise.html\">ENTREPRISE</a>, <a href=\"produits-description.html\">DESCRIPTION</a>.\n"+
            "            <table>\n";
            for(int j=0;j<length(tabtrier,1); j++) {
            content+="              <tr>\n"+
            "                <td>"+tabtrier[j][0]+"</td><td>"+tabtrier[j][1]+"</td><td>"+tabtrier[j][2]+"</td><td>"+tabtrier[j][3]+"</td><td>"+tabtrier[j][4]+"</td>\n"+
            "              </tr>\n";
            }
            content+="            </table>\n"+
            "          </p>\n"+
            "      </section>\n"+
            "    </main>\n"+
            "  </body>\n"+
            "</html>";
            stringAsFile("output/produits-" + nomparametre[i]+".html", content);
        }

    }

    void algorithm() {
        // Chargement des fichiers data/produitX.html dans la structure de données
        String[][] produits = chargerProduits("data", "produit");
        //println(toString(produits));

        println("Création de la page index.html");
        final String TITLE = "Ordinateurs mythiques";
        final String PAGE_ACCUEIL = genererAccueil(TITLE, produits);
        stringAsFile("output/index.html", PAGE_ACCUEIL);

        // Création de l'ensemble des pages HTML des produits
        print("Création de la page produitX.html : ");
        for (int numeroProduit = 0; numeroProduit < length(produits, 1); numeroProduit = numeroProduit + 1) {
            final String PAGE_PRODUIT = genererPageProduit(TITLE, produits, numeroProduit);
            final String IDX_NOM_FICHIER  = (numeroProduit + 1)+".html";
            stringAsFile("output/produit" + IDX_NOM_FICHIER, PAGE_PRODUIT);
            print((numeroProduit + 1)+" ");
            //println(PAGE_PRODUIT);
        }
        GenererListeProduit();
        println();
    }
}