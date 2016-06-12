/**
 * Created by Andrew Bell 6/11/2016
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2016. See license.txt for details.
 */

package com.recursivechaos.gamemanager.repository;

import com.recursivechaos.gamemanager.domain.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Integer> {

    List<Game> findByName(String name);

}
