package com.springinaction.tacos.data;

import com.springinaction.tacos.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
