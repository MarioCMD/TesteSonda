package br.com.testesonda.api.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.lang.reflect.Field;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.testesonda.api.entity.Aeronave;
import br.com.testesonda.api.repository.AeronaveRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AeronaveController {
	@Autowired
	private AeronaveRepository aeronaveRepository;
	
	@RequestMapping(value = "/aeronave", method = RequestMethod.GET)
	public List<Aeronave> get() {
		return aeronaveRepository.findAll();
	}
	
	@RequestMapping(value = "/aeronave/{id}", method = RequestMethod.GET)
    public ResponseEntity<Aeronave> getById(@PathVariable(value = "id") long id)
    {
        Optional<Aeronave> aeronave = aeronaveRepository.findById(id);
        if(aeronave.isPresent())
            return new ResponseEntity<Aeronave>(aeronave.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@RequestMapping(value = "/aeronave", method =  RequestMethod.POST)
    public Aeronave post(@Valid @RequestBody Aeronave aeronave)
    {
		aeronave.setCreated(new Date());
        return aeronaveRepository.save(aeronave);
    }
	
	 @RequestMapping(value = "/aeronave/{id}", method =  RequestMethod.PUT)
	    public ResponseEntity<Aeronave> put(@PathVariable(value = "id") long id, @Valid @RequestBody Aeronave newAeronave)
	    {
	        Optional<Aeronave> oldAeronave = aeronaveRepository.findById(id);
	        if(oldAeronave.isPresent()){
	            Aeronave aeronave = oldAeronave.get();
	            aeronave.setNome(newAeronave.getNome());
	            aeronave.setAno(newAeronave.getAno());
	            aeronave.setDescricao(newAeronave.getDescricao());
	            aeronave.setMarca(newAeronave.getMarca());
	            aeronave.setVendido(newAeronave.isVendido());
	            aeronave.setUpdated(new Date());
	            aeronaveRepository.save(aeronave);
	            return new ResponseEntity<Aeronave>(aeronave, HttpStatus.OK);
	        }
	        else
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	 
	 @RequestMapping(value = "/aeronave/{id}", method = RequestMethod.DELETE)
	    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id)
	    {
	        Optional<Aeronave> aeronave = aeronaveRepository.findById(id);
	        if(aeronave.isPresent()){
	            aeronaveRepository.delete(aeronave.get());
	            return new ResponseEntity<>(HttpStatus.OK);
	        }
	        else
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	
	 @RequestMapping(value = "aeronave/{id}", method = RequestMethod.PATCH)
	 	public ResponseEntity<Aeronave> Patch(@PathVariable(value = "id") long id, @RequestBody Map<Object, Object> fields){
		 Optional<Aeronave> oldAeronave = aeronaveRepository.findById(id);
		 if(oldAeronave.isPresent()){
			 Aeronave aeronave = oldAeronave.get();
			 fields.forEach((k, v) -> {
				 Field field = ReflectionUtils.findRequiredField(Aeronave.class, (String) k);
				 field.setAccessible(true);
				 ReflectionUtils.setField(field, aeronave, v);
			 });		 
			 aeronave.setUpdated(new Date());
			 aeronaveRepository.save(aeronave);
			 return new ResponseEntity<Aeronave>(aeronave, HttpStatus.OK);
		 } else
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	 }
}
