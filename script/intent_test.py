# /// script
# dependencies = [
#   "rich",
# ]
# ///

import subprocess
import os
from rich.console import Console
from rich.panel import Panel
from rich.table import Table
from rich.prompt import Prompt

console = Console()

PACKAGE_NAME = "com.school_of_company.intent_sample_project"

def run_adb(command: str, description: str):
    console.print(Panel(f"[bold blue]실행 중:[/bold blue] {description}\n[dim]{command}[/dim]"))
    try:
        # result = subprocess.run(command, shell=True, capture_output=True, text=True) # Output capturing can be messy for some adb outputs
        # Using os.system or subprocess without capture to show output directly if needed, 
        # but let's stick to subprocess for cleaner control.
        result = subprocess.run(command, shell=True, capture_output=True, text=True)
        if result.returncode == 0:
            console.print("[bold green]성공![/bold green]")
            if result.stdout:
                console.print(result.stdout.strip())
        else:
            console.print(f"[bold red]오류 (Exit Code {result.returncode}):[/bold red]")
            console.print(result.stderr.strip())
    except Exception as e:
        console.print(f"[bold red]명령어 실행 실패:[/bold red] {e}")

def show_menu():
    table = Table(title="Android Intent 테스트 메뉴", show_header=True, header_style="bold magenta")
    table.add_column("번호", style="dim", width=6)
    table.add_column("테스트 항목")
    
    table.add_row("1", "명시적 인텐트 테스트 (DetailActivity 호출)")
    table.add_row("2", "암시적 인텐트 테스트 (공유 수신 확인)")
    table.add_row("3", "브로드캐스트 리시버 테스트")
    table.add_row("4", "보안 확인 (exported=false 확인)")
    table.add_row("0", "종료")
    
    console.print(table)

def main():
    while True:
        os.system('clear' if os.name == 'posix' else 'cls')
        show_menu()
        choice = Prompt.ask("실행할 테스트 번호를 선택하세요", choices=["1", "2", "3", "4", "0"], default="0")
        
        if choice == "1":
            cmd = f'adb shell "am start -n {PACKAGE_NAME}/.DetailActivity --es \'EXTRA_TITLE\' \'화면이동 테스트\' --ei \'EXTRA_ID\' 123"'
            run_adb(cmd, "명시적 인텐트 테스트 (DetailActivity)")
        elif choice == "2":
            cmd = f'adb shell "am start -a android.intent.action.SEND -t \'text/plain\' --es \'android.intent.extra.TEXT\' \'텍스트 공유 테스트\' {PACKAGE_NAME}"'
            run_adb(cmd, "암시적 인텐트 테스트 (ShareActivity)")
        elif choice == "3":
            cmd = f'adb shell "am broadcast -a com.school_of_company.CUSTOM_ACTION --es \'EXTRA_MESSAGE\' \'시그널 테스트\' {PACKAGE_NAME}"'
            run_adb(cmd, "브로드캐스트 리시버 테스트 (Custom Action)")
        elif choice == "4":
            cmd = f'adb shell "am start -n {PACKAGE_NAME}/.DetailActivity"'
            run_adb(cmd, "보안 확인 (exported=false 확인)")
        elif choice == "0":
            console.print("[yellow]프로그램을 종료합니다.[/yellow]")
            break
            
        Prompt.ask("\n계속하려면 Enter를 누르세요...")

if __name__ == "__main__":
    main()
