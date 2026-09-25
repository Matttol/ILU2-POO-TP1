package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private final String nom;
	private Chef chef;
	private final Gaulois[] villageois;
	private int nbVillageois = 0;
	private final Marche marche;

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

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit){
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int etalLibre = this.marche.trouverEtalLibre();
		this.marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
		chaine.append("Le vendeur " + vendeur + " vend des " + produit + " à l'étal n° " + etalLibre + ".\n");
		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit){
		StringBuilder chaine = new StringBuilder();
		int nbVendeurs = 0;
		Gaulois[] vendeurs = new Gaulois[this.marche.getNbEtalsMax()];
		for (int i =0; i< marche.nbEtalsMax; i++){
			if (this.marche.etals[i].contientProduit(produit)){
				vendeurs[nbVendeurs] = this.marche.etals[i].getVendeur();
				nbVendeurs++;
			}
		}
		switch(nbVendeurs){
			case 0:
				chaine.append("Il n'y a pas de vendeur qui propose des " + produit + " au marché.\n");
				break;
			case 1:
				chaine.append("Seul le vendeur " + vendeurs[0] + " propose des " + produit + " au marché.\n");
				break;
			default:
				chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
				for (int i = 0; i<nbVendeurs; i++) chaine.append("- " + vendeurs[i] + "\n");
				break;
		}
		return chaine.toString();
	}

	private static class Marche {
		private final Etal[] etals;
		private int nbEtalsMax;


		private Marche (int nbEtalsMax) {
			etals = new Etal[nbEtalsMax];
			for (int i = 0; i<nbEtalsMax; i++){
				etals[i] = new Etal();
			}
		}

		private int getNbEtalsMax(){
			return this.nbEtalsMax;
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit){
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre(){
			for (int i = 0; i<nbEtalsMax; i++){
				if (!etals[i].isEtalOccupe()) {
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
				if (etals[i].contientProduit(produit)) {
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
			StringBuilder chaineMarche = new StringBuilder();
			int i = 0;
			for (i = 0; i<nbEtalsMax; i++){
				while (etals[i] != null){
					chaineMarche.append(etals[i].afficherEtal());
				}
			}
			if (i<nbEtalsMax-1) chaineMarche.append(String.format("Il reste %d étals non utilisés dans le marché.\n",nbEtalsMax-i));
			return chaineMarche.toString();
		}

	}

}