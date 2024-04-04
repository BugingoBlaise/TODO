package org.blaisesolutions.viewModels;



import org.blaisesolutions.entity.Person;
import org.blaisesolutions.services.PersonService;
 import org.zkoss.bind.annotation.*;
 import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;

import java.util.ArrayList;
import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PersonViewModel {

	@WireVariable(PersonService.NAME)
	private PersonService personService;
	private String username;
	private Integer age;
	private byte[] imageData;

	List<Person>personList=new ArrayList<>();

	@Init
	public void init() {
		getallpersons();
	}
	@NotifyChange("personList")
	public void getallpersons(){
		personList=personService.queryAll();
	}
	@Command
	@NotifyChange("imageData")
	public void uploadImage(@BindingParam("media") Media media) {
		if (media != null && media.isBinary()) {
			imageData = media.getByteData();
		}
	}
	@NotifyChange("personList")
	@Command
	public void deletePerson(@BindingParam("person") Person person) {
		String re=personService.softDelete(person);
		Clients.showNotification(re);
		getallpersons(); // Refresh the person list
	}
	@NotifyChange("personList")
	@Command
	public void addPerson() {
		if (username == null || username.isEmpty()) {
			Clients.showNotification("Username is required", Clients.NOTIFICATION_TYPE_ERROR, null, null, 2000);
			return;
		}
		if (age == null || age <= 0) {
			Clients.showNotification("Age must be a positive number", Clients.NOTIFICATION_TYPE_ERROR, null, null, 2000);
			return;
		}
		Person person = new Person();
		person.setUsername(username);
		person.setAge(age);
//		person.setImageData(imageData); // Set image data directly

		String msg = personService.save(person);
		Clients.showNotification(msg);
	}

	public List<Person> getPersonList() {
		List<Person> activePersons = new ArrayList<>();
		for (Person person : personList) {
			if (!person.isDeleted()) {
				activePersons.add(person);
			}
		}
		return activePersons;
	}



	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}





	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}




public void setPersonList(List<Person> personList) {
	this.personList = personList;
}

}
