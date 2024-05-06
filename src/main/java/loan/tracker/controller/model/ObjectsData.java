package loan.tracker.controller.model;

import loan.tracker.entity.AnObject;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ObjectsData {
	
		private Long objectId;
		private String catalogNumber;
		private String commonName;
		private String medium;
		
		public ObjectsData(AnObject object) {
			this.objectId = object.getObjectId();
			this.catalogNumber = object.getCatalogNumber();
			this.commonName = object.getCommonName();
			this.medium = object.getMedium();		
		}
		
		public AnObject toObject() {
			AnObject object = new AnObject();
			
			object.setObjectId(objectId);
			object.setCatalogNumber(catalogNumber);
			object.setCommonName(commonName);
			object.setMedium(medium);
			
			return object;
		}
		
		public ObjectsData(Long objectId, String catalogNumber, String commonName, String medium) {
			this.objectId = objectId;
			this.catalogNumber = catalogNumber;
			this.commonName = commonName;
			this.medium = medium;
		}
	}


