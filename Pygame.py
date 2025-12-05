import pygame
from sys import exit

pygame.init()

screen = pygame.display.set_mode((800, 400))
pygame.display.set_caption("Gayme Window")
clock = pygame.time.Clock()
game_active = True

sky_surface = pygame.image.load('Pygame Intro/Graphics/Sky.png').convert_alpha()
ground_surface = pygame.image.load('Pygame Intro/Graphics/ground.png').convert_alpha()

score = 0
score_surface = pygame.font.Font('Pygame Intro/font/Pixeltype.ttf', 50).render('Score: ' + str(score), False, (64,64,64)).convert_alpha()
score_rect = score_surface.get_rect(center=(400, 50))

player_surface = pygame.image.load('Pygame Intro/Graphics/Player/player_walk_1.png').convert_alpha()
player_rect = player_surface.get_rect(midbottom=(80, 300))
player_gravity = 0

snail_surface = pygame.image.load('Pygame Intro/Graphics/snail/snail1.png').convert_alpha()
snail_rect = snail_surface.get_rect(midbottom=(600, 300))
snail_speed = 4

while True:

    for event in pygame.event.get():

        if event.type == pygame.QUIT:
            pygame.quit()
            exit()

        if game_active:

            if player_rect.collidepoint(pygame.mouse.get_pos()) and event.type == pygame.MOUSEBUTTONDOWN and player_rect.bottom >= 300:
                    player_gravity = -20

            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_SPACE and player_rect.bottom >= 300:
                    player_gravity = -20

    if game_active:

        keys = pygame.key.get_pressed()

        screen.blit(sky_surface, (0, 0))

        screen.blit(ground_surface, (0, 300))

        pygame.draw.rect(screen, "#c0e8ec", score_rect)
        screen.blit(score_surface, score_rect)

        screen.blit(snail_surface, snail_rect)

        player_gravity += 0.75
        player_rect.y += player_gravity
        if player_rect.bottom >= 300:
            player_rect.bottom = 300
        screen.blit(player_surface, player_rect)

        if (snail_rect.right) < 0:
            snail_rect.left = 800
            score += 1
            score_surface = pygame.font.Font('Pygame Intro/font/Pixeltype.ttf', 50).render('Score: ' + str(score), False, (64,64,64)).convert_alpha()

        snail_rect.left -= snail_speed

        if player_rect.colliderect(snail_rect):
            game_active = False

    else:

        screen.fill((94, 129, 162))

        game_over_surface = pygame.font.Font('Pygame Intro/font/Pixeltype.ttf', 50).render('Game Over! Click to Restart. Final Score: '+str(score), False, (255,255,255)).convert_alpha()
        game_over_rect = game_over_surface.get_rect(center=(400, 200))
        screen.blit(game_over_surface, game_over_rect)

        if event.type == pygame.MOUSEBUTTONDOWN:
            game_active = True
            snail_rect.left = 800
            score = 0

    pygame.display.update()
    clock.tick(60)
