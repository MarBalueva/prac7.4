import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Deque;
import java.util.LinkedList;

public class Test extends JFrame {
    public int k = 0;
    public static Integer whoIsWin(Integer pl1, Integer pl2) {
        if (pl1 == 0 && pl2 == 9)
            return pl1;
        if (pl2 == 0 && pl1 == 9)
            return pl2;
        return pl1 > pl2 ? pl1 : pl2;
    }

    Test(){
        Deque<Integer> player1 = new LinkedList<>(); //дек первого игрока
        Deque<Integer> player2 = new LinkedList<>(); //дек второго игрока

        JFrame input_window = new JFrame("The drunkard's game"); //ввод данных
        JFrame output_window = new JFrame("The drunkard's game"); //ход игры
        input_window.setSize(500, 200);

        JTextField cards1 = new JTextField(9); //карты первого
        JTextField cards2 = new JTextField(9); //карты второго
        JLabel input_player1 = new JLabel("Input 5 cards for the first player:");
        JLabel input_player2 = new JLabel("Input 5 cards for the second player:");
        JButton input_ready = new JButton("OK");

        JPanel[] input_panel = new JPanel[6];
        input_window.setLayout(new GridLayout(3,2));
        for (int i = 0; i < input_panel.length; i++)
        {
            input_panel[i] = new JPanel();
            input_window.add(input_panel[i]);
        }
        input_panel[0].add(input_player1, BorderLayout.WEST);
        input_panel[1].add(cards1);
        input_panel[2].add(input_player2, BorderLayout.WEST);
        input_panel[3].add(cards2);
        input_panel[4].add(input_ready, BorderLayout.EAST);

        input_ready.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String pl1 = cards1.getText(); //карты первого игрока
                    String pl2 = cards2.getText(); //карты второго игрока
                    for (int i = 0; i < 9; i+=2) //исключаем пробелы
                    {
                        player1.addLast(Character.getNumericValue(pl1.charAt(i)));
                        player2.addLast(Character.getNumericValue(pl2.charAt(i)));
                    }
                    input_window.dispose();
                    output_window.setVisible(true);
                }
                catch (Exception exception) {};
            }
        });
        input_window.setVisible(true);

        output_window.setSize(600, 300);
        output_window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        output_window.setVisible(false);

        JLabel winner = new JLabel("Who is winner?");
        JLabel steps = new JLabel("Step: "); //номер хода
        JLabel output_player1 = new JLabel("Player 1");
        JLabel output_player2 = new JLabel("Player 2");
        JLabel player1_cards = new JLabel(player1.toString()); //карты первого
        JLabel player2_cards = new JLabel(player2.toString()); //карты второго
        JLabel step_player1 = new JLabel("");
        JLabel step_player2 = new JLabel("");
        JButton current_step = new JButton("Step!"); //индикатор хода

        JPanel[] output_panel = new JPanel[12];
        output_window.setLayout(new GridLayout(3,4));
        for (int i = 0; i < output_panel.length; i++)
        {
            output_panel[i] = new JPanel();
            output_window.add(output_panel[i]);
        }
        output_panel[1].add(winner, BorderLayout.CENTER);
        output_panel[1].add(steps, BorderLayout.SOUTH);
        output_panel[4].add(output_player1, BorderLayout.NORTH);
        output_panel[5].add(step_player1, BorderLayout.NORTH);
        output_panel[6].add(step_player2, BorderLayout.NORTH);
        output_panel[7].add(output_player2, BorderLayout.NORTH);
        output_panel[8].add(player1_cards, BorderLayout.SOUTH);
        output_panel[11].add(player2_cards, BorderLayout.SOUTH);
        output_panel[9].add(current_step, BorderLayout.SOUTH);
        k = 0; //количество ходов
        current_step.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                        try {
                            k++;
                            int f_pl = player1.peekFirst(); //карта первого игрока
                            int s_pl = player2.peekFirst(); //второго игрока
                            step_player1.setText(Integer.toString(f_pl));
                            step_player2.setText(Integer.toString(s_pl));

                            if (whoIsWin(f_pl, s_pl) == f_pl) {
                                player1.removeFirst(); //удаляем карту у первого игрока
                                if (k % 2 == 1) {
                                    player1.addLast(f_pl); //кладем в дек первого игрока карту второго игрока
                                    player1.addLast(s_pl); //кладем в дек первого игрока его карту
                                } else {
                                    player1.addLast(s_pl); //кладем в дек первого игрока карту второго игрока
                                    player1.addLast(f_pl); //кладем в дек первого игрока его карту
                                }
                                player2.removeFirst(); //удаляем карту у второго игрока (меньшую)
                            } else {
                                player2.remove(); //удаляем карту у второго игрока
                                if (k % 2 == 1) {
                                    player2.addLast(f_pl); //кладем в дек первого игрока карту второго игрока
                                    player2.addLast(s_pl); //кладем в дек первого игрока его карту
                                } else {
                                    player2.addLast(s_pl); //кладем в дек первого игрока карту второго игрока
                                    player2.addLast(f_pl); //кладем в дек первого игрока его карту
                                }
                                player1.removeFirst(); //удаляем карту у первого игрока(меньшую)
                            }
                            player1_cards.setText(player1.toString());
                            player2_cards.setText(player2.toString());
                            if (k >= 106) {
                                winner.setText("BOTVA");
                            }
                            steps.setText("Step: " + k);
                            if (player1.isEmpty() == true)
                                winner.setText("The second is winner!");
                            else
                            if (player2.isEmpty() == true)
                                winner.setText("The first is winner!");
                        }
                        catch (Exception exception) {}
                    }
        });
        while (player1.isEmpty() != true) //ход игры
            if (player2.isEmpty() != true)
                current_step.getActionCommand();
            else break;
        }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Test();
            }
        });
    }
}