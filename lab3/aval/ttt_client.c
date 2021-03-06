/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "ttt.h"

#define TTT_UNUSED_PLAY_RES 5

void ttt_1(host) char *host;
{
  int player = 0; /* Player number - 0 or 1               */
  int go = 0;     /* Square selection number for turn     */
  int row = 0;    /* Row index for a square               */
  int column = 0; /* Column index for a square            */
  // int winner = -1;                              /* The winning player */  int
  // play_res;  char buffer[MAX_BUFFER_LEN];
  CLIENT *clnt;
  char **result_1;
  char *currentboard_1_arg;
  int *play_res;
  play_args play_1_arg;
  int *winner;
  char *checkwinner_1_arg;
  plays  *result_4;
	char *test_1_arg;
  clnt = clnt_create(host, TTT, V1, "udp");

  /* The main game loop. The game continues for up to 9 turns */
  /* As long as there is no winner                            */
  do {
    /* Get valid player square selection */
    do {
      /* Print current board */
      result_1 = currentboard_1((void *)&currentboard_1_arg, clnt);
      if (result_1 == NULL) {
        clnt_perror(clnt, "call failed:");
      }
      printf("%s\n", *result_1);

      printf(
          "\nPlayer %d, please enter the number of the square "
          "where you want to place your %c (or 0 to refresh the board): ",
          player, (player == 1) ? 'X' : 'O');
      scanf(" %d", &go);

      if(go==-1){
        result_4 = test_1((void*)&test_1_arg, clnt);
	      if (result_4 == (plays *) NULL) {
		      clnt_perror (clnt, "call failed");
	      }
        printf("Moves:\n\nPlayer 0: %d\nPlayer 1: %d\n",result_4->player2,result_4->player1);
        *play_res = TTT_UNUSED_PLAY_RES;
        continue;
      }

      if (go == 0) {
        *play_res = TTT_UNUSED_PLAY_RES;
        continue;
      }

      // row = --go/3;                                 /* Get row index of
      // square      */  column = go%3;                                /* Get
      // column index of square   */
      play_1_arg.row = --go / 3;
      play_1_arg.column = go % 3;
      play_1_arg.player = player;
      play_res = play_1(&play_1_arg, clnt);
      if (play_res == NULL) {
        clnt_perror(clnt, "call failed:");
      }
      if (*play_res != 0) {
        switch (*play_res) {
          case 1:
            printf("Position outside board.");
            break;
          case 2:
            printf("Square already taken.");
            break;
          case 3:
            printf("Not your turn.");
            break;
          case 4:
            printf("Game has finished.");
            break;
        }
        printf(" Try again...\n");
      }
    } while (*play_res != 0);

    winner = checkwinner_1((void *)&checkwinner_1_arg, clnt);
    if (winner == NULL) {
      clnt_perror(clnt, "call failed:");
    }
    player = (player + 1) % 2; /* Select player */

    printf("player %d\n", player);

  } while (*winner == -1);

  /* Game is over so display the final board */
  result_1 = currentboard_1((void *)&currentboard_1_arg, clnt);
  if (result_1 == NULL) {
    clnt_perror(clnt, "call failed:");
  }
  printf("%s\n", *result_1);

  /* Display result message */
  if (*winner == 2)
    printf("\nHow boring, it is a draw\n");
  else
    printf("\nCongratulations, player %d, YOU ARE THE WINNER!\n", *winner);
}

int main(argc, argv) int argc;
char *argv[];
{
  char *host;

  if (argc < 2) {
    printf("usage: %s server_host\n", argv[0]);
    exit(1);
  }
  host = argv[1];
  ttt_1(host);
}