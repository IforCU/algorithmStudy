package iforcu;

import java.util.*;
import java.io.*;

/**
 * [문제명] 스티커 붙이기
 * [링크] https://www.acmicpc.net/problem/18808
 * [난이도] 골드 3
 * [설명] 노트북 격자에 스티커를 차례대로 붙이는 문제입니다.
 * 각 스티커는 회전(0도, 90도, 180도, 270도)을 시도하며 붙일 수 있는 가장 위쪽, 왼쪽 위치에 붙입니다.
 * [풀이] 구현 + 시뮬레이션
 * 1. 각 스티커마다 회전하지 않은 상태부터 시계방향 90도씩 회전하며 붙일 수 있는지 확인
 * 2. 노트북의 위쪽부터 탐색하며 붙일 수 있는 첫 번째 위치에 스티커 부착
 * 3. 네 가지 회전 모두 실패하면 해당 스티커는 버림
 * 4. 최종적으로 노트북에 붙은 스티커 칸의 개수를 출력
 */
public class Main_BOJ_18808 {
    static int N, M, K;
    static int[][] grid;
    static List<int[][]> stikers;

    public static void main(String[] args) throws IOException {
        // [입력 처리] 노트북 크기와 스티커 정보 입력
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        grid = new int[N][M];
        stikers = new ArrayList<>();

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int[][] stiker = new int[r][c];
            for (int j = 0; j < r; j++) {
                st = new StringTokenizer(br.readLine());
                for (int j2 = 0; j2 < c; j2++) {
                    stiker[j][j2] = Integer.parseInt(st.nextToken());
                }
            }
            stikers.add(stiker);
        }

        // [스티커 붙이기] 각 스티커를 0도, 90도, 180도, 270도 회전하며 붙이기 시도
        for(int[][] stiker : stikers) {
            int[][] curStiker = stiker;
            for (int k = 0; k < 4; k++) {
                if(attach(curStiker)) break;
                curStiker = rotate(curStiker);
            }
        }

        // [결과 계산] 노트북에서 스티커가 붙은 칸의 개수 세기
        int answer = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if(grid[i][j] == 1) answer++;
            }
        }
        System.out.println(answer);
    }

    // [스티커 부착 시도] 노트북의 위쪽, 왼쪽부터 스티커를 붙일 수 있는 위치 탐색
    static boolean attach(int[][] stiker) {
        int r = stiker.length;
        int c = stiker[0].length;
        for(int i = 0; i <= N-r; i++) {
            for (int j = 0; j <= M-c; j++) {
                if(isSet(stiker, i, j)) {
                    setStiker(stiker, i ,j);
                    return true;
                }
            }
        }
        return false;
    }

    // [부착 가능 여부 확인] 현재 위치에 스티커를 붙일 수 있는지 확인 (겹치지 않는지 체크)
    static public boolean isSet(int[][] stiker, int i, int j) {
        int r = stiker.length;
        int c = stiker[0].length;
        for (int R = 0; R < r; R++) {
            for (int C = 0; C < c; C++) {
                if(stiker[R][C] == 1 && grid[i+R][j+C] == 1){
                    return false;
                }
            }  
        }
        return true;
    }

    // [스티커 붙이기] 노트북에 스티커를 실제로 부착
    static public void setStiker(int[][] stiker, int i, int j) {
        int r = stiker.length;
        int c = stiker[0].length;
        for (int R = 0; R < r; R++) {
            for (int C = 0; C < c; C++) {
                if (stiker[R][C] == 1) grid[i + R][j + C] = 1;
            }  
        }
    }
    
    // [스티커 회전] 스티커를 시계방향으로 90도 회전
    static public int[][] rotate(int[][] block) {
        int r = block.length;
        int c = block[0].length;
        int[][] newBlock = new int[c][r];

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                newBlock[j][r - 1 - i] = block[i][j];
            }
        }
        return newBlock;
    }
}
