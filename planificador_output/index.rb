require 'terminal-table'
require 'pp'
require 'table_print'
require './overrides'
require './read_char'
tp.set :max_width, 20000

row_headers_info = []
raw_gantt_data = []
eval(gets, binding).

process_count = row_headers_info.size
tasks_data = raw_gantt_data.first(process_count)
colas_bloqueados = raw_gantt_data[process_count..process_count + 3]
cpu_data = raw_gantt_data.last(2)

def format_raw_gantt_data(tasks_array)
	char_map = {
		'0' => ' ',
		'-1' => 'A',
		'-2' => 'B',
		'-3' => 'X',
	}
  tasks_array.map { |x| char_map[x] || x }.join
end

def format_data_for_table(processes_array, tasks_array, step_number)
  matrixRowIndex = 0
    processes_array.each do |process|
    process.klts.each do |klt|
      if klt.ults.empty?
        klt.ults << { name: '', tasks: format_raw_gantt_data(tasks_array[matrixRowIndex].first(step_number) )}
        matrixRowIndex += 1
      else
        klt.ults.each do |ult|
          ult[:tasks] = format_raw_gantt_data(tasks_array[matrixRowIndex].first(step_number))
          matrixRowIndex += 1
        end
      end
    end
  end
end

def show_single_key
  c = read_char

  case c
  when "\e[C"
    puts "RIGHT ARROW"
  when "\e[D"
    puts "LEFT ARROW"
  when "\u0003"
    puts "CONTROL-C"
  end
end

# RIGHT_ARROW = "\e[C"
# LEFT_ARROW = "\e[D"
LEFT_ARROW = "a"
RIGHT_ARROW = "d"
CONTROL_C = "\u0003"
SPACE = ' '
step_number = 0
autoplay_on = false

def print_references(step_number, autoplay_on)
  puts "\n\n\n"
  puts "STEP: #{step_number}"
  puts "AUTOPLAY: #{autoplay_on ? 'ON' : 'OFF'}"
  puts "\n\n\n"
  puts "Presione el comando deseado y luego ENTER"
  previous_step = { key: 'A', reference: 'For previous step'}
  next_step = { key: 'D', reference: 'For next step'}
  quit_command = { key: 'CTRL + C', reference: 'To quit'}
  autoplay_stop_command = { key: 'Space', reference: 'Autoplay/Stop'}
  commands = [previous_step, next_step, quit_command, autoplay_stop_command]
  tp(commands)
end

def print_table(processes, tasks, step_number)
  format_data_for_table(processes, tasks, step_number)
  tp(processes, {'name' => { display_name: 'Process' }}, {'klts.name' => { display_name: 'KLT'}}, {'klts.ults.name' => { display_name: 'ULT' }}, {'klts.ults.tasks' => { display_name: 'Tasks' }})
end

system "clear" or system "cls"

# Read status of queues from log file
logfile = File.open("../log.txt", "rb")
contents = logfile.read
queueslogged = contents.split("-----")

def print_logged_step(step_number, queues)
  puts "\n"
  puts queues[step_number - 1]
end

loop do
  print_table(row_headers_info, raw_gantt_data, step_number)
  print_logged_step(step_number, queueslogged) unless step_number == 0
  print_references(step_number, autoplay_on)
  c = read_char2
  case c
  when RIGHT_ARROW
    step_number += 1
  when LEFT_ARROW
    step_number.positive? && step_number -= 1
  when CONTROL_C
    exit 0
  when SPACE
    autoplay_on = !autoplay_on
  end
  puts c
  sleep 0.5
  step_number += 1 if autoplay_on
  system "clear" or system "cls"
end
