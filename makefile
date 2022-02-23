JCC = javac
JFLAGS = -implicit:none -encoding UTF-8 -d build -classpath build -sourcepath "src"
SRC = src/fr/iutfbleau/projetIHM2020FI2/
BUILD = build/fr/iutfbleau/projetIHM2020FI2/

default: $(BUILD)test/Menu.class $(BUILD)test/IndicationUpdate.class $(BUILD)test/IndicationHandler.class $(BUILD)test/Indication.class $(BUILD)test/Textures.class $(BUILD)test/MiniMap.class $(BUILD)test/InventaireSac.class $(BUILD)test/Coffre.class $(BUILD)test/Coord.class $(BUILD)test/Map.class $(BUILD)test/ActionDoor.class $(BUILD)test/Controller.class $(BUILD)test/Fenetre.class $(BUILD)test/Camera.class $(BUILD)test/TestTexteMP.class $(BUILD)test/TestTexteMNP.class $(BUILD)MP/TrucP.class $(BUILD)MP/TrucFactoryP.class $(BUILD)MP/PieceP.class $(BUILD)MP/PassagePieceFactoryP.class $(BUILD)MP/PassageP.class $(BUILD)MP/JoueurP.class $(BUILD)MP/ContientTrucsP.class $(BUILD)MP/ConnexionBDD.class $(BUILD)MNP/TrucNP.class $(BUILD)MNP/TrucFactoryNP.class $(BUILD)MNP/PieceNP.class $(BUILD)MNP/PassagePieceFactoryNP.class $(BUILD)MNP/PassageNP.class $(BUILD)MNP/JoueurNP.class $(BUILD)MNP/ContientTrucsNP.class $(BUILD)API/TypeTruc.class $(BUILD)API/TrucFactory.class $(BUILD)API/Truc.class $(BUILD)API/Piece.class $(BUILD)API/PassagePieceFactory.class $(BUILD)API/Passage.class $(BUILD)API/Joueur.class $(BUILD)API/EtatPassage.class $(BUILD)API/Direction.class $(BUILD)API/Descriptible.class $(BUILD)API/ContientTrucs.class $(BUILD)API/Activable.class

$(BUILD)API/Activable.class: $(SRC)API/Activable.java
	$(JCC) $(JFLAGS) $(SRC)API/Activable.java
$(BUILD)API/ContientTrucs.class: $(SRC)API/ContientTrucs.java
	$(JCC) $(JFLAGS) $(SRC)API/ContientTrucs.java
$(BUILD)API/Descriptible.class: $(SRC)API/Descriptible.java
	$(JCC) $(JFLAGS) $(SRC)API/Descriptible.java
$(BUILD)API/Direction.class: $(SRC)API/Direction.java
	$(JCC) $(JFLAGS) $(SRC)API/Direction.java
$(BUILD)API/EtatPassage.class: $(SRC)API/EtatPassage.java
	$(JCC) $(JFLAGS) $(SRC)API/EtatPassage.java
$(BUILD)API/Joueur.class: $(SRC)API/Joueur.java
	$(JCC) $(JFLAGS) $(SRC)API/Joueur.java
$(BUILD)API/Passage.class: $(SRC)API/Passage.java
	$(JCC) $(JFLAGS) $(SRC)API/Passage.java
$(BUILD)API/PassagePieceFactory.class: $(SRC)API/PassagePieceFactory.java
	$(JCC) $(JFLAGS) $(SRC)API/PassagePieceFactory.java
$(BUILD)API/Piece.class: $(SRC)API/Piece.java
	$(JCC) $(JFLAGS) $(SRC)API/Piece.java
$(BUILD)API/Truc.class: $(SRC)API/Truc.java
	$(JCC) $(JFLAGS) $(SRC)API/Truc.java
$(BUILD)API/TrucFactory.class: $(SRC)API/TrucFactory.java
	$(JCC) $(JFLAGS) $(SRC)API/TrucFactory.java	
$(BUILD)API/TypeTruc.class: $(SRC)API/TypeTruc.java
	$(JCC) $(JFLAGS) $(SRC)API/TypeTruc.java



$(BUILD)MNP/ContientTrucsNP.class: $(SRC)MNP/ContientTrucsNP.java
	$(JCC) $(JFLAGS) $(SRC)MNP/ContientTrucsNP.java
$(BUILD)MNP/JoueurNP.class: $(SRC)MNP/JoueurNP.java
	$(JCC) $(JFLAGS) $(SRC)MNP/JoueurNP.java
$(BUILD)MNP/PassageNP.class: $(SRC)MNP/PassageNP.java
	$(JCC) $(JFLAGS) $(SRC)MNP/PassageNP.java
$(BUILD)MNP/PassagePieceFactoryNP.class: $(SRC)MNP/PassagePieceFactoryNP.java
	$(JCC) $(JFLAGS) $(SRC)MNP/PassagePieceFactoryNP.java
$(BUILD)MNP/PieceNP.class: $(SRC)MNP/PieceNP.java
	$(JCC) $(JFLAGS) $(SRC)MNP/PieceNP.java	
$(BUILD)MNP/TrucFactoryNP.class: $(SRC)MNP/TrucFactoryNP.java
	$(JCC) $(JFLAGS) $(SRC)MNP/TrucFactoryNP.java	
$(BUILD)MNP/TrucNP.class: $(SRC)MNP/TrucNP.java
	$(JCC) $(JFLAGS) $(SRC)MNP/TrucNP.java	



$(BUILD)MP/ContientTrucsP.class: $(SRC)MP/ContientTrucsP.java
	$(JCC) $(JFLAGS) $(SRC)MP/ContientTrucsP.java
$(BUILD)MP/JoueurP.class: $(SRC)MP/JoueurP.java
	$(JCC) $(JFLAGS) $(SRC)MP/JoueurP.java
$(BUILD)MP/PassageP.class: $(SRC)MP/PassageP.java
	$(JCC) $(JFLAGS) $(SRC)MP/PassageP.java
$(BUILD)MP/PassagePieceFactoryP.class: $(SRC)MP/PassagePieceFactoryP.java
	$(JCC) $(JFLAGS) $(SRC)MP/PassagePieceFactoryP.java
$(BUILD)MP/PieceP.class: $(SRC)MP/PieceP.java
	$(JCC) $(JFLAGS) $(SRC)MP/PieceP.java	
$(BUILD)MP/TrucFactoryP.class: $(SRC)MP/TrucFactoryP.java
	$(JCC) $(JFLAGS) $(SRC)MP/TrucFactoryP.java	
$(BUILD)MP/TrucP.class: $(SRC)MP/TrucP.java
	$(JCC) $(JFLAGS) $(SRC)MP/TrucP.java
$(BUILD)MP/ConnexionBDD.class: $(SRC)MP/ConnexionBDD.java
	$(JCC) $(JFLAGS) $(SRC)MP/ConnexionBDD.java	


$(BUILD)test/TestTexteMNP.class: $(SRC)test/TestTexteMNP.java
	$(JCC) $(JFLAGS) $(SRC)test/TestTexteMNP.java
$(BUILD)test/TestTexteMP.class: $(SRC)test/TestTexteMP.java
	$(JCC) $(JFLAGS) $(SRC)test/TestTexteMP.java
$(BUILD)test/Controller.class: $(SRC)test/Controller.java
	$(JCC) $(JFLAGS) $(SRC)test/Controller.java	
$(BUILD)test/Camera.class: $(SRC)test/Camera.java
	$(JCC) $(JFLAGS) $(SRC)test/Camera.java	
$(BUILD)test/Fenetre.class: $(SRC)test/Fenetre.java
	$(JCC) $(JFLAGS) $(SRC)test/Fenetre.java
$(BUILD)test/ActionDoor.class: $(SRC)test/ActionDoor.java
	$(JCC) $(JFLAGS) $(SRC)test/ActionDoor.java
$(BUILD)test/Map.class: $(SRC)test/Map.java
	$(JCC) $(JFLAGS) $(SRC)test/Map.java
$(BUILD)test/Coord.class: $(SRC)test/Coord.java
	$(JCC) $(JFLAGS) $(SRC)test/Coord.java
$(BUILD)test/Coffre.class: $(SRC)test/Coffre.java
	$(JCC) $(JFLAGS) $(SRC)test/Coffre.java
$(BUILD)test/InventaireSac.class: $(SRC)test/InventaireSac.java
	$(JCC) $(JFLAGS) $(SRC)test/InventaireSac.java
$(BUILD)test/MiniMap.class: $(SRC)test/MiniMap.java
	$(JCC) $(JFLAGS) $(SRC)test/MiniMap.java
$(BUILD)test/Textures.class: $(SRC)test/Textures.java
	$(JCC) $(JFLAGS) $(SRC)test/Textures.java
$(BUILD)test/Indication.class: $(SRC)test/Indication.java
	$(JCC) $(JFLAGS) $(SRC)test/Indication.java
$(BUILD)test/IndicationHandler.class: $(SRC)test/IndicationHandler.java
	$(JCC) $(JFLAGS) $(SRC)test/IndicationHandler.java
$(BUILD)test/IndicationUpdate.class: $(SRC)test/IndicationUpdate.java
	$(JCC) $(JFLAGS) $(SRC)test/IndicationUpdate.java
$(BUILD)test/Menu.class: $(SRC)test/Menu.java
	$(JCC) $(JFLAGS) $(SRC)test/Menu.java

jar:
	jar cvfe projet.jar fr.iutfbleau.projetIHM2020FI2.test.TestTexteMNP -C build fr -C build org -C res images
clean:
	rm -Rf $(BUILD)*