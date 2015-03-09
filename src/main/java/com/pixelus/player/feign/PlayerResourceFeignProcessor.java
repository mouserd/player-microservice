package com.pixelus.player.feign;

import com.pixelus.player.model.Player;
import com.pixelus.player.model.PlayerStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

@Component
public class PlayerResourceFeignProcessor implements ResourceProcessor<Resource<Player>> {

    @Autowired
    private PlayerStatsFeignClient playerStatsFeignClient;

    @Override
    public Resource<Player> process(final Resource<Player> playerResource) {

        final Player player = playerResource.getContent();
        final PlayerStats playerStats = playerStatsFeignClient.getPlayerStatsById(player.getId());
        if (playerStats != null) {
            player.setFeignStats(playerStats);
        }

        return playerResource;
    }
}
