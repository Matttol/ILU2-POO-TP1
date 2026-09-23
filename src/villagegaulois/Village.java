package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtalsMax) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtalsMax);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	private static class Marche {
		private Etal[] etals;
		private int nbEtalsMax;


		private Marche (int nbEtalsMax) {
			etals = new Etal[nbEtalsMax];
			for (int i = 0; i<nbEtalsMax; i++){
				etals[i] = new Etal();
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit){
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre(){
			for (int i = 0; i<nbEtalsMax; i++){
				if (etals[i].isEtalOccupe() == false) {
					return i;
				}
			}
			return -1;
		}

		private Etal[] trouverEtals (String produit) {
			Etal[] etalsProduit;
			etalsProduit = new Etal[nbEtalsMax];
			int y = 0;
			for (int i = 0; i<nbEtalsMax; i++){
				if (etals[i].contientProduit(produit) == true) {
					etalsProduit[y] = etals[i];
					y++;
				}
			}
			return etalsProduit;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i<nbEtalsMax; i++){
				if (etals[i].getVendeur() == gaulois){
					return etals[i];
				}
			}
			return null;
		}

		private String afficherMarche(){
			StringBuilder marche = new StringBuilder("");
			int i = 0;
			for (i = 0; i<nbEtalsMax; i++){
				while (etals[i] != null){
					marche.append(etals[i].afficherEtal());
				}
			}
			marche.append(String.format("Il reste %d étals non utilisés dans le marché.\n",nbEtalsMax-i));
			String desc = marche.toString();
			return desc;
		}

	}

}